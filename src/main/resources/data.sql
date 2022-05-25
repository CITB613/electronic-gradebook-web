DELETE FROM USERS_AUTHORITIES WHERE USER_ID = (
    SELECT ID FROM USERS WHERE USERNAME = 'admin');

DELETE FROM USERS WHERE USERNAME = 'admin';
DELETE FROM ROLES;

INSERT INTO ROLES (ID, AUTHORITY) VALUES 
    (1, 'ROLE_ADMIN'), 
    (2, 'ROLE_PRINCIPAL'),
    (3, 'ROLE_TEACHER'), 
    (4, 'ROLE_PARENT'),
    (5, 'ROLE_STUDENT');
