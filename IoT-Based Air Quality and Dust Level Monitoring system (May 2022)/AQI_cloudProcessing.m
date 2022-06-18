%locate data 
Pollutants_ChannelID = 1726321;
Pollutants_readAPIKey = 'OB9KHUCZHY8QE5VM';
Pollutants_WriteKey = 'LTOF7TBMME20KGJ6';
%%% 
Averges_ChannelID = 1675270;
Averges_readAPIKey = '2Q378BIQWO7XFB3R';
Averges_WriteKey = 'CYPM3LK5TX915LP6';
%%%
AQI_ChannelID = 1698133;
AQI_readAPIKey = '6ZQP808KU9FXB401';
AQI_WriteKey = 'ILU09B0IVBI97CDN';


%% Read Data


%PM2.5 data
[PM25_rawData,PM25_time]= thingSpeakRead(Pollutants_ChannelID, 'Field', 4,...
    'NumMinutes', 1440,'ReadKey', Pollutants_readAPIKey); 


rowIdx = find(isnan(PM25_rawData));
PM25_time(rowIdx) = [];
PM25_rawData = PM25_rawData(~isnan(PM25_rawData));
PM25_localTime = PM25_time + hours(3); 


%PM10 data
[PM10_rawData,PM10_time]= thingSpeakRead(Pollutants_ChannelID, 'Field', 5,...
    'NumMinutes', 1440,'ReadKey', Pollutants_readAPIKey); 


rowIdx = find(isnan(PM10_rawData));
PM10_time(rowIdx) = [];
PM10_rawData = PM10_rawData(~isnan(PM10_rawData));
PM10_localTime = PM10_time + hours(3); 


%CO, 8 Hours data
[CO_8h_rawData,CO_8h_time]= thingSpeakRead(Pollutants_ChannelID, 'Field', 6,...
    'NumMinutes', 480, 'ReadKey', Pollutants_readAPIKey); 


rowIdx = find(isnan(CO_8h_rawData));
CO_8h_time(rowIdx) = [];
CO_8h_rawData = CO_8h_rawData(~isnan(CO_8h_rawData));
CO_8h_localTime = CO_8h_time + hours(3);


%% Find subAQI


%------------ 24 Hours average


return_PM25 = main(PM25_localTime,PM25_rawData,1)
return_PM10 = main(PM10_localTime,PM10_rawData,2)


%------------ 8 Hours average
return_CO_8h = main(CO_8h_localTime,CO_8h_rawData,3);
%% - Averges SAVED in ThingSpeak Channel


thingSpeakWrite(Averges_ChannelID,'Fields',[1],'Values',{return_CO_8h},'WriteKey',Averges_WriteKey);


% ------------ max 8 Hours saved averages
% ----CO-----


[ALL_CO_8h,ALL_CO_8h_time] =thingSpeakRead(Averges_ChannelID, 'Field', 1,...
    'NumMinutes', 1440, 'ReadKey', Averges_readAPIKey); 
rowIdx = find(isnan(ALL_CO_8h));
ALL_CO_8h_time(rowIdx) = [];
ALL_CO_8h = ALL_CO_8h(~isnan(ALL_CO_8h));
%ALL_CO_8h_localTime = ALL_CO_8h_time + hours(3);
Max_CO_8h= max(ALL_CO_8h)


%% AQI & matlab plot 
%---------AQI----------
return_AQI = max([return_PM25 return_PM10  Max_CO_8h ]) % Max_O3_8h Max_O3_1h Max_NO2_1h
thingSpeakWrite(AQI_ChannelID,return_AQI,'WriteKey',AQI_WriteKey,'Fields',1);
linkdata on
plotfun(return_AQI)
%Functions: -
%main




function return_subAQI = main(localTime,rawData,pollutant)
smoothData = movmedian(rawData,10);


% Combine smoothed data with time as # of elements are the same
smoothDataTable = table(localTime,smoothData,'VariableNames',{'Time','Concentration'});


% Calculate AQI
data_mean = round(mean(smoothDataTable{:,2}),1); % in general mean of data_observed
return_subAQI = breakpoints(data_mean,pollutant);
end
%% Breakpoints


function result = breakpoints(data_mean,pollutant)


%AQI - levels
aqiLow  = [0;51;101;151;201;301];
aqiHigh = [50;100;150;200;300;500];


table_AQI = table(aqiLow,aqiHigh, ...
    'VariableNames',{'AQI_low','AQI_high'});
%brakpoints
if (pollutant == 1)
	   PM25_Low = [0;12.1;35.5;55.5;150.5;250.5];
    PM25_High = [12;35.4;55.4;150.4;250.4;99999];
    table_PM25 = table(PM25_Low,PM25_High,... 
    'VariableNames',{'low','high'});
    result = equation(data_mean,table_AQI,table_PM25);
elseif (pollutant == 2)
    PM10_Low = [0;55;155;255;355;425];
    PM10_High = [54;154;254;354;424;99999];
    table_PM10 = table(PM10_Low,PM10_High,...
        'VariableNames',{'low','high'});
    result = equation(data_mean,table_AQI,table_PM10);
elseif (pollutant == 3)
    CO_8h_Low = [0;4.5;9.5;12.5;15.5;30.5];
    CO_8h_High = [4.4;9.4;12.4;15.4;30.4;99999];
    table_CO_8h = table(CO_8h_Low,CO_8h_High,...
        'VariableNames',{'low','high'});
    result = equation(data_mean,table_AQI,table_CO_8h);
end
end
% sub-AQI Equation


function result = equation(data_mean,table_AQI,table_pollution)
    rowIdx = find(data_mean >= table_pollution.low & data_mean <= table_pollution.high);
		conc_min = table_pollution.low(rowIdx);
		conc_max = table_pollution.high(rowIdx);
		AQI_min = table_AQI.AQI_low(rowIdx);
		AQI_max = table_AQI.AQI_high(rowIdx);


		result = round((((data_mean - conc_min) .* (AQI_max - AQI_min))/(conc_max - conc_min)) + AQI_min);
end
%% Plot Data


function plotfun(rawData)
		hold on
		plot(rawData,"-*");
		title('AQI')
		xlabel('Time');
		ylabel('Concentration \mug/m^{3}');
		legend('Collected data','Location','best')
		axis tight
		hold off
end
