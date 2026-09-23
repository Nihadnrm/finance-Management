create table email(
                        id bigint primary key auto_increment,
                        user_id bigint,
                        user_name varchar(255),
                        message varchar(255),
                        foreign key(user_id)references auth_users(id)

);