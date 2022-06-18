CREATE TABLE book(book_id INT NOT NULL ,book_name VARCHAR ( 40 ),TYPE VARCHAR ( 30 ),catagory VARCHAR ( 30 ),PRIMARY KEY (book_id));CREATE TABLE author(book_id INT ,author_name VARCHAR ( 30 ),FOREIGN KEY (book_id)REFERENCES book(book_id),PRIMARY KEY (book_id,author_name));CREATE TABLE branch(b_id INT NOT NULL ,NAME VARCHAR ( 50 ),location VARCHAR ( 30 ),phone1 INT ,phone2 INT ,PRIMARY KEY (b_id));CREATE TABLE branch_book(book_id INT NOT NULL ,availability VARCHAR ( 20 ) NOT NULL ,b_id INT NOT NULL ,PRIMARY KEY (book_id, b_id),FOREIGN KEY (book_id)REFERENCES book(book_id),FOREIGN KEY (b_id) REFERENCESbranch(b_id));CREATE TABLE privilage(privilege_id INT NOT NULL ,book_duration VARCHAR ( 30 ),loan_period VARCHAR ( 30 ),PRIMARY KEY (privilege_id));CREATE TABLE sevices_privilage(privilege_id INT ,service_name VARCHAR ( 30 ),PRIMARY KEY (privilege_id,service_name),FOREIGN KEY (privilege_id)REFERENCES privilage(privilege_id),FOREIGN KEY (service_name)REFERENCESother_services(service_name));CREATE TABLE staff(S_id INT NOT NULL ,F_name VARCHAR ( 30 ) NOT NULL ,L_name VARCHAR ( 30 ),phone INT ,b_id INT ,PRIMARY KEY (S_id),FOREIGN KEY (b_id) REFERENCESbranch(b_id));CREATE TABLE other_services(service_name VARCHAR ( 30 ) NOT NULL ,describtion VARCHAR ( 40 ),PRIMARY KEY (service_name) );
CREATE TABLE members(
member_id INT NOT NULL ,
Password INT ,
F_name VARCHAR ( 30 ),
L_name VARCHAR ( 30 ),
member_type VARCHAR ( 30 ),
email VARCHAR ( 40 ),
DOB DATE ,
privilege_id INT ,
PRIMARY KEY (member_id),
FOREIGN KEY (privilege_id)
REFERENCES privilages(privilege_id)
);
CREATE TABLE loan(
loan_number INT NOT NULL ,
due_date DATE ,
member_id INT ,
b_id INT ,
PRIMARY KEY (loan_number),
FOREIGN KEY (member_id)
REFERENCES members(member_id),
FOREIGN KEY (b_id) REFERENCES
branch(b_id)
);
CREATE TABLE fine(
loan_number INT ,
amount INT ,
DATE DATE ,
state VARCHAR ( 30 ),
discribtion VARCHAR ( 40 ),
FOREIGN KEY (loan_number)
REFERENCES loan(loan_number)
);
CREATE TABLE room(
Room_number VARCHAR ( 10 ) NOT NULL ,
size VARCHAR ( 30 ),
b_id INT ,
PRIMARY KEY (Room_number),
FOREIGN KEY (b_id) REFERENCES
branch(b_id)
);
CREATE TABLE room_book(
duration VARCHAR ( 30 ),
book_id INT NOT NULL ,
DATE DATE ,
Room_number VARCHAR ( 10 ),
member_id INT NOT NULL ,
PRIMARY KEY (Room_number,
member_id),
FOREIGN KEY (Room_number)
REFERENCES room(Room_number),
FOREIGN KEY (member_id)
REFERENCES members(member_id)
);
CREATE TABLE branch_service(
service_name VARCHAR ( 30 ),
b_id INT ,
PRIMARY KEY (service_name, b_id),
FOREIGN KEY (service_name) REFERENCES
other_services(service_name),
FOREIGN KEY (b_id) REFERENCES
branch(b_id) );