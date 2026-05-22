-- V2__insert_initial_data.sql
INSERT INTO todo (title, done) VALUES
                                   ('跟着路线亲手跑通框架', false),
                                   ('感受自动化的力量', false);

INSERT INTO sys_user (username, password, role, enabled) VALUES
                                                             ('admin', '$2a$12$0Ha1qTxhw3jomoWOZtTbheIdSyjL09o3zkEre0qcSQKXPA.SyzzHO', 'ROLE_ADMIN', true),
                                                             ('liuli', '$2a$12$0Ha1qTxhw3jomoWOZtTbheIdSyjL09o3zkEre0qcSQKXPA.SyzzHO', 'ROLE_USER', true);