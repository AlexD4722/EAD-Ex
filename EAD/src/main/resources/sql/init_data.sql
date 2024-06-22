# // create table script
create table student_t (
                           student_id int auto_increment primary key,
                           student_code varchar(20) not null,
                           full_name varchar(100) not null,
                           address varchar(255)
);
create table subject_t (
                           subject_id int auto_increment primary key,
                           subject_code varchar(20) not null,
                           subject_name varchar(100) not null,
                           credit int not null
);
create table student_score_t (
                                 student_score_id int auto_increment primary key,
                                 student_id int,
                                 subject_id int,
                                 score1 decimal(5,2),
                                 score2 decimal(5,2),
                                 constraint fk_student_id foreign key (student_id) references
                                     student_t(student_id),
                                 constraint fk_subject_id foreign key (subject_id) references
                                     subject_t(subject_id)
);
# // Insert sample data
insert into subject_t (subject_code, subject_name, credit) VALUES
('JAVA', 'Java Programming', 4),
('PHP', 'PHP Programming', 3),
('WDA', 'Web Development and Applications', 3);
insert into student_t (student_code, full_name, address)
VALUES ('2007A10', 'Nguyễn Văn A', 'Hà Nội');
insert into student_score_t (student_id, subject_id, score1,score2)
VALUES (1, 1, 8.5,7.0);