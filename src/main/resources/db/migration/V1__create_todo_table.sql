-- V1__create_todo_table.sql
-- 创建待办事项表

CREATE TABLE IF NOT EXISTS todo (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    done BOOLEAN DEFAULT FALSE
    );

-- 创建认证用户表
CREATE TABLE IF NOT EXISTS sys_user (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE
    );