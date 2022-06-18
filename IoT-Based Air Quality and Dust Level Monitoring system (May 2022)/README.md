**IoT-Based Air Quality and Dust Level Monitoring system (May 2022)**

Air quality effected by a number of pollutants that identified by World Health Organization (WHO), this project check them and tells at the end how clean the air is.

Design the system:

- Defining the requirement of the project.
- Comparing different approaches and creating a new one.
- Using flowchart, DFD, Activity diagram which how the project works.

Hardware Configuration:

- Connect air quality and dust ****sensors with a microcontroller with the right wiring.
- Create a c++ program to upload it to the microcontroller, This program will:
    - Connect microcontroller to network.
    - Some sensors need to be collaborated.
    - Translate sensors values to a valid meaning.
    - Upload sensed data to a cloud.

Cloud processes with MATLAB:

- Prepare data to enter the next equations.
- Create breakpoints tables to be used in sub-aqi equation.
- Find the sub-AQI for each pollutant.
- Find the overall AQI.

Android App using Java and XML:

- Bring information from cloud.
- Classify it into WHO categories and show the current AQI value as well the category.
- Notify user when air quality is in unhealthy level.


**Team:**
- Renad Alsweed 381210443
- Hind Alzaidan 381213135
- Shahad Altuwijri 381213454
