#!/usr/bin/python3.9

from tkinter import *
import tkinter.font as tkFont 
from random import *
from tkinter import messagebox

lis = [[0 for x in range(9)] for y in range(9)]
#diffrent coppies of the frist list(lis) used in diffrent conditions
x = [[0 for x in range(9)] for y in range(9)]
y = [[0 for x in range(9)] for y in range(9)]
z = [[0 for x in range(9)] for y in range(9)]

def print_board(board):
    for i in range(len(board)):
        if i % 3 == 0 and i != 0:
            print("- - - - - - - - - - - - - ")

        for j in range(len(board[0])):
            if j % 3 == 0 and j != 0:
                print(" | ", end="")

            if j == 8:
                print(board[i][j])
            else:
                print(str(board[i][j]) + " ", end="")


def find_empty(board):
    for i in range(len(board)):
        for j in range(len(board[0])):
            if board[i][j] == 0:
                return (i, j)  # row, col

def solve():
    find = find_empty(lis)
    if not find:
        return True
    else:
        row, col = find

    for i in range(1,10):
        if valid(lis, (row, col),i ):
            lis[row][col] = i

            if solve():
                return True

            lis[row][col] = 0

   
    return False


def valid(board, position,num):
    # Check row
    for i in range(len(board[0])):
        if board[position[0]][i] == num and position[1] != i:
            return False

    # Check column
    for i in range(len(board)):
        if board[i][position[1]] == num and position[0] != i:
            return False

    # Check box
    boardx_x = (position[1] // 3) * 3
    boardx_y = (position[0] // 3) * 3

    for i in range(boardx_y, boardx_y + 3):
        for j in range(boardx_x, boardx_x + 3):
            if board[i][j] == num and (i, j) != position:
                return False

    return True


def ALLvalid():
    
    for row in range(9):
        for col in range (9):

            # Check row
            for i in range(len(z[row])):
                print('check row: row,col,i',row,',',col,',',i)
                if z[row][i] == z[row][col] and col != i:
                    print('in row')
                    return False

            # Check column
            for j in range(len(z)):
                print('check col: row,col,j',row,',',col,',',j)
                if z[j][col] == z[row][col] and row != j:
                    print('in col')
                    return False

            # Check box
            row_x = (row // 3) *3
            col_y = (col // 3) *3

            for i in range(row_x,row_x+3):
                for j in range(col_y,col_y+3):
                    print('check box: row,col,(i,j)', row, ',', col, ',(', i,',',j,')')
                    if z[i][j] == z[row][col] and (i,j) != (row,col):
                        print('in box')
                        return False
            
           
            
    return True




	
def sudoku():
    
    sudoku_grid = [[0 for x in range(9)] for y in range(9)]
    main_window = Tk()
    sudoku_font = tkFont.Font(size=12, weight="bold")
    by_font = tkFont.Font(size=8 )
    button_text_font = tkFont.Font(size=8, weight="bold")
    top_frame = Frame(main_window)
    mid_frame = Frame(main_window, bg='white', width=700, height=700)
    bottom_frame = Frame(main_window)

    lable_title = Label(top_frame, text='Sudoku Game', font=sudoku_font)
    lable_by = Label(top_frame, text='By: Asmaa & Rinad & Rahaf  ^_^  ', font=by_font, fg='Blue')
    
    for i in range(9):
        for j in range(9):
            y[i][j]=lis[i][j]	
			
    def Start_New():
        for i in range(9):
            for j in range(9):
                lis[i][j]= 0
        fillboard() 
        for i in range(9):
            for j in range(9):
                y[i][j]=lis[i][j]

    def Clear_input():
        for i in range(9):
            for j in range(9):
                
                if(x[i][j]==0):
                    va_padx=2
                    va_pady=2
                    if(i==2 or i==5):
                        va_pady=13
                    if(j==2 or j==5):
                        va_padx=13
                    sudoku_grid = Entry(mid_frame, width=3, bg='light blue', fg='black', justify='center')
                    sudoku_grid.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=3, ipady=3)
                    if (y[i][j]!=0):
                        sudoku_grid_lable = Label(mid_frame, text=lis[i][j], width=3, bg='light blue', fg='black', font=button_text_font)
                        sudoku_grid_lable.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=0, ipady=2)
 
                elif(1,2,3,4,5,6,7,8,9) not in x:
                        pass
                elif(x[i][j]==0): 
                    va_padx=2
                    va_pady=2
                    if(i==2 or i==5):
                        va_pady=13
                    if(j==2 or j==5):
                        va_padx=13
                    sudoku_grid = Entry(mid_frame, width=3, bg='light blue', fg='black', justify='center')
                    sudoku_grid.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=3, ipady=3)
                    #sudoku_grid_x.grid_forget()
                    #sudoku_grid.delete(0)

    def so():
        for i in range(9):
            for j in range(9):
                x[i][j]=lis[i][j]
                if 0 not in x:
                    x[i][j]=y[i][j]
                
        solve()
        print_board(lis)
        for i in range(9):
            for j in range(9):
                va_padx=2
                va_pady=2
                if(i==2 or i==5):
                    va_pady=13
                if(j==2 or j==5):
                    va_padx=13
                if (y[i][j]!=0):
                    sudoku_grid_lable = Label(mid_frame, text=lis[i][j], width=3, bg='light blue', fg='black', font=button_text_font)
                    sudoku_grid_lable.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=0, ipady=2)
                    z[i][j]=lis[i][j]
                elif(x[i][j]== 0):
                    x_filed=StringVar(mid_frame, value=lis[i][j])
                    sudoku_grid = Entry(mid_frame, width=3, bg='light blue', fg='black', textvariable=x_filed, justify='center')
                    sudoku_grid.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=3, ipady=3)
                    val=mid_frame.grid_slaves(row=i, column=j)[0]
                    z[i][j]=val.get()
                    
        
    def fillboard():			
        generate = randrange(10,15)
# choose random numbers

        for N in range(generate):

            row = randrange(9)
            col = randrange(9)
            num = randrange(1, 10)
            while (not valid(lis, (row, col),  num) or lis[row][col] != 0):  # Fill the position if not taken or not valid
                row = randrange(9)
                col = randrange(9)
                num = randrange(1, 10)
            lis[row][col] = num

        
        print_board(lis)
        
# fill the random numbers
        
				
        for i in range(9):
            for j in range(9):
                va_padx=2
                va_pady=2
                if(i==2 or i==5):
                    va_pady=13
                if(j==2 or j==5):
                    va_padx=13
                if (lis[i][j]!=0):
                    sudoku_grid_lable = Label(mid_frame, text=lis[i][j], width=3, bg='light blue', fg='black', font=button_text_font)
                    sudoku_grid_lable.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=0, ipady=2)
                elif(lis[i][j]== 0):
                    sudoku_grid = Entry(mid_frame, width=3, bg='light blue', fg='black' , justify='center')
                    sudoku_grid.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=3, ipady=3)


    fillboard()
         
    def Response():
        #print_board(z)
        for i in range(9):
            for j in range(9):
                va_padx=2
                va_pady=2
                if(i==2 or i==5):
                    va_pady=13
                if(j==2 or j==5):
                    va_padx=13
                if(y[i][j]!=0):
                    sudoku_grid_lable = Label(mid_frame, text=lis[i][j], width=3, bg='light blue', fg='black', font=button_text_font)
                    sudoku_grid_lable.grid(row=i, column=j, padx=(0, va_padx), pady=(0, va_pady), ipadx=0, ipady=2)
                    z[i][j]=y[i][j]
                if(y[i][j]==0):
                    val=mid_frame.grid_slaves(row=i, column=j)[0]
                    z[i][j]=val.get()
                    #print(val.get())
        #print_board(z)            

        check= ALLvalid()
        if (check):
            messagebox.showinfo('Response', \
                            'Excelent Answer, I Think You Cheated, *_* \
In Case You Didn\'t, Then Well Done Genius ^_^ ^_^')
        else:
             messagebox.showinfo('Response', \
                            'Try Again ~_~')
    for i in range(9):
        for j in range(9):
            y[i][j]=lis[i][j]

	
    Check_button = Button(bottom_frame, text='Check', width=12, bg='blue', fg='White', font=button_text_font,command =Response)
    StartNew_button = Button(bottom_frame, text='Start New', width=12, bg='blue', fg='White', font=button_text_font,command =Start_New)
    Clear_button = Button(bottom_frame, text='Clear', width=12, bg='blue', fg='White', font=button_text_font, command =Clear_input)
    Solve_button = Button(bottom_frame, text='Solve', width=12, bg='blue', fg='White', font=button_text_font, command =so)

    lable_title.pack(side='top', pady=(5, 0))
    lable_by.pack(side='bottom', pady=(0, 0))

    Check_button.pack(side='left', padx=5)
    StartNew_button.pack(side='left')
    Clear_button.pack(side='left', padx=5,)
    Solve_button.pack(side='left', padx=5,)

    top_frame.pack(pady=(10, 0))
    mid_frame.pack(padx=20, pady=10)
    bottom_frame.pack(padx=20, pady=(5, 20))


    mainloop()


sudoku()
