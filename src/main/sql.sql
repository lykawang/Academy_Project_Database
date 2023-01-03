drop table NewGrads;
drop table Coop;
drop table Resume;
drop table StudentWorker;
drop table RequiresSkill;
drop table HasSkill;
drop table Skill;
drop table Posts;
drop table JobBoard;
drop table Application;
drop table Offer;
drop table Job;
drop table Company;
drop table Location;
drop table Advisor;
drop table Student;

create table Student
  (studentID int not null,
  sname varchar(30) not null,
  major varchar(50) null,
  primary key (studentID));

grant select on Student to public;

create table Advisor
  (workerID int not null,
  aname varchar(30) not null,
  focus_major varchar(50) not null,
  primary key (workerID));

grant select on Advisor to public;

create table NewGrads
  (studentID int not null,
  degree varchar(20) not null,
  primary key (studentID),
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE);

grant select on NewGrads to public;

create table Coop
  (studentID int not null,
  year_level int default 1,
  completed_coop_term int default 0,
  workerID int not null,
  primary key (studentID),
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE,
  foreign key (workerID) references Advisor(workerID) ON DELETE CASCADE);

grant select on Coop to public;

create table Resume
  (file_name varchar(50) not null,
  studentID int not null,
  viewed int default 0,
  primary key (file_name, studentID),
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE);

grant select on Resume to public;

create table StudentWorker
   (studentID int not null,
   workerID int null,
   primary key (studentID),
   foreign key (studentID) references Student(studentID) ON DELETE CASCADE,
   foreign key (workerID) references Advisor(workerID));

grant select on StudentWorker to public;

create table Location
  (postal_code varchar(7) not null,
  city varchar(30) not null,
  province varchar(20) not null,
  primary key (postal_code));

grant select on Location to public;

create table Company
  (companyID int not null,
  cname varchar(60) not null,
  postal_code varchar(7),
  primary key (companyID),
  foreign key (postal_code) references Location(postal_code) ON DELETE SET NULL);

grant select on Company to public;

create table Job
  (jobID int not null,
  jname varchar(50) not null,
  companyID int not null,
  postal_code varchar(7),
  primary key (jobID),
  foreign key (companyID) references Company(companyID) ON DELETE CASCADE,
  foreign key (postal_code) references Location(postal_code) ON DELETE SET NULL);

grant select on Job to public;

create table Application
  (applicationID int not null,
  jobID int not null,
  studentID int not null,
  file_name varchar(50) not null,
  primary key (applicationID),
  foreign key (jobID) references Job(jobID) ON DELETE CASCADE,
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE);

grant select on Application to public;

create table Skill
  (skill_name varchar(30) not null,
  primary key (skill_name));

grant select on Skill to public;

create table RequiresSkill
  (skill_name varchar(30) not null,
  jobID int not null,
  rproficiency varchar(20) default 'beginner',
  primary key (skill_name, jobID),
  foreign key (skill_name) references Skill(skill_name) ON DELETE CASCADE,
  foreign key (jobID) references Job(jobID) ON DELETE CASCADE);

grant select on RequiresSkill to public;

create table HasSkill
  (skill_name varchar(30) not null,
  studentID int not null,
  hproficiency varchar(20) default 'beginner',
  primary key (skill_name, studentID),
  foreign key (skill_name) references Skill(skill_name) ON DELETE CASCADE,
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE);

grant select on HasSkill to public;

create table JobBoard
  (jbname varchar(30) not null,
  primary key(jbname));

grant select on JobBoard to public;

create table Posts
  (jobID int not null,
  jbname varchar(30) not null,
  date_posted date not null,
  primary key (jobID, jbname),
  foreign key (jobID) references Job(jobID) ON DELETE CASCADE,
  foreign key (jbname) references JobBoard(jbname) ON DELETE CASCADE);

grant select on Posts to public;

create table Offer
  (jobID int not null,
  studentID int not null,
  primary key (jobID, studentID),
  foreign key (jobID) references Job(jobID) ON DELETE CASCADE,
  foreign key (studentID) references Student(studentID) ON DELETE CASCADE);

grant select on Offer to public;


insert into Student
values('50819036','Timothy Ma','Computer Science');

insert into Student
values('71249940','Lyka Wang','Computer Science');

insert into Student
values('33334456','Code Chen','cat');

insert into Student
values('12345678','Dummy Chen','cat');

insert into Student
values('87654321','Rachel Chen','earth Science');

insert into Student
values('25245887','Haoyuan Chen','natural Science');

insert into Advisor
values ('213468915', 'Green', 'Cat');

insert into Advisor
values('238957766', 'Cheryl', 'natural science');

insert into Advisor
values('998723567', 'Alice', 'math');

insert into Advisor
values('899462035', 'Gregor', 'Computer Science');

insert into Advisor
values('722515454', 'Michel','arts');

insert into Coop
values('50819036','3','0','213468915');

insert into Coop
values('71249940','3','0','899462035');

insert into Coop
values('33334456','1','0','213468915');

insert into Coop
values('12345678','1','0','213468915');

insert into NewGrads
values('87654321', 'Bachelor');

insert into NewGrads
values('25245887', 'Diploma');

insert into Resume
values('Haoyuans resume', '25245887','10');

insert into Resume
values('Timothys resume', '50819036','10');

insert into Resume
values('lykas resume', '71249940','20');

insert into Resume
values('dummys resume', '12345678','15');

insert into Resume
values('codes resume', '33334456','15');

insert into StudentWorker
values('50819036','899462035');

insert into StudentWorker
values('71249940','899462035');

insert into StudentWorker
values('25245887','238957766');

insert into StudentWorker
values('12345678','238957766');

insert into Location
values ('V6T 1Z4', 'Vancouver', 'British Columbia');

insert into Location
values ('M5J 2W7', 'Toronto', 'Ontario');

insert into Location
values ('V3B 7T2', 'Port Coquitlam', 'British Columbia');

insert into Location
values ('V6E 3X2', 'Vancouver', 'British Columbia');

insert into Location
values ('98109', 'Seattle', 'Washington');

insert into Location
values ('L4W 4Y1', 'Mississauga', 'Ontario');

insert into Location
values ('V7Y 1K4', 'Vancouver', 'British Columbia');

insert into Company
values ('0001', 'Nokia', 'L4W 4Y1');

insert into Company
values ('0002', 'EY', 'V6E 3X2');

insert into Company
values ('0003', 'Amazon', '98109');

insert into Company
values ('0004', 'Sony Pictures Imageworks', 'V7Y 1K4');

insert into Company
values ('0005', 'RBC', 'M5J 2W7');

insert into Company
values ('0006', 'TTT Studios', 'V7Y 1K4');

insert into Job
values ('1001', 'Software Engineer', '0001', 'L4W 4Y1');

insert into Job
values ('1002', 'Software Developer in Test', '0001', 'V6T 1Z4');

insert into Job
values ('1003', 'Software Developer in Test', '0002', 'V6E 3X2');

insert into Job
values ('1004', 'Software Developer Front-end', '0002', 'V6E 3X2');

insert into Job
values ('1005', 'Java Backend Developer', '0002', 'V6E 3X2');

insert into Job
values ('1006', 'Cloud DevOps Engineer', '0003', '98109');

insert into Job
values ('1007', 'Software Engineer', '0003', '98109');

insert into Job
values ('1008', 'Software Developer in Test', '0003', '98109');

insert into Job
values ('1009', 'Software Engineer', '0003', 'V7Y 1K4');

insert into Application
values ('1', '1001', '50819036', 'application_Timothy_Ma');

insert into Application
values ('2', '1002', '50819036', 'application_Timothy_Ma');

insert into Application
values ('3', '1003', '50819036', 'application_Amazon_Timothy');

insert into Application
values ('4', '1001', '71249940', 'application_Lyka');

insert into Application
values ('5', '1001', '25245887', 'application_Haoyuan');

insert into Application
values ('6', '1004', '71249940', 'application_Lyka');

insert into Application
values ('7', '1004', '25245887', 'application_Haoyuan');

insert into Application
values ('8', '1004', '87654321', 'application');

insert into Skill
values ('Python');

insert into Skill
values ('Java');

insert into Skill
values ('HTML');

insert into Skill
values ('C#');

insert into Skill
values ('JavaScript');

insert into RequiresSkill
values ('Python', '1001', 'advanced');

insert into RequiresSkill
values ('Python', '1002', 'intermediate');

insert into RequiresSkill
values ('Java', '1002', 'intermediate');

insert into RequiresSkill
values ('Java', '1003', 'intermediate');

insert into RequiresSkill
values ('C#', '1003', 'beginner');

insert into RequiresSkill
values ('HTML', '1004', 'advanced');

insert into RequiresSkill
values ('JavaScript', '1004', 'advanced');

insert into RequiresSkill
values ('C#', '1005', 'advanced');

insert into RequiresSkill
values ('JavaScript', '1005', 'advanced');

insert into HasSkill
values ('Python', '50819036', 'intermediate');

insert into HasSkill
values ('Java', '50819036', 'advanced');

insert into HasSkill
values ('HTML', '50819036', 'advanced');

insert into HasSkill
values ('C#', '50819036', 'beginner');

insert into HasSkill
values ('JavaScript', '50819036', 'advanced');

insert into HasSkill
values ('Python', '71249940', 'advanced');

insert into HasSkill
values ('Java', '71249940', 'intermediate');

insert into HasSkill
values ('HTML', '71249940', 'advanced');

insert into HasSkill
values ('C#', '71249940', 'beginner');

insert into HasSkill
values ('JavaScript', '71249940', 'intermediate');

insert into HasSkill
values ('Python', '25245887', 'advanced');

insert into HasSkill
values ('Java', '25245887', 'advanced');

insert into HasSkill
values ('HTML', '25245887', 'intermediate');

insert into HasSkill
values ('C#', '25245887', 'beginner');

insert into HasSkill
values ('JavaScript', '25245887', 'intermediate');

insert into HasSkill
values ('Python', '87654321', 'intermediate');

insert into HasSkill
values ('Java', '87654321', 'intermediate');

insert into JobBoard
values ('LinkedIn');

insert into JobBoard
values ('indeed');

insert into JobBoard
values ('WorkBC');

insert into Posts
values ('1001','LinkedIn', DATE '2022-11-01');

insert into Posts
values ('1001','indeed', DATE '2022-11-02');

insert into Posts
values ('1001','WorkBC', DATE '2022-11-04');

insert into Posts
values ('1002','LinkedIn', DATE '2022-11-10');

insert into Posts
values ('1002','indeed', DATE '2022-11-07');

insert into Posts
values ('1002','WorkBC', DATE '2022-11-07');

insert into Posts
values ('1003','LinkedIn', DATE '2022-11-02');

insert into Posts
values ('1004','LinkedIn', DATE'2022-11-02');

insert into Offer
values ('1001', '25245887');

insert into Offer
values ('1002', '25245887');

insert into Offer
values ('1003', '25245887');

insert into Offer
values ('1004', '50819036');

insert into Offer
values ('1004', '71249940');
