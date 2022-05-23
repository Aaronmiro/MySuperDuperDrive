# MySuperDuperDrive

### [Quick start](http://104.131.45.178:8078)
- **username**: aaronmiro
- **password**: 123456

### This project includes three user-facing features:

1. **Simple File Storage**: Upload/download/remove files
2. **Note Management**: Add/update/remove text notes
3. **Password Management**: Save, edit, and delete website credentials

### Three layers of the application have been implemented: 
1. The back-end with **Spring Boot**
2. The front-end with **Thymeleaf**
3. Application tests with **Selenium**

## 1. The Back-End
The back-end is all about security and connecting the front-end to database data and actions.

1. Managing user access with **Spring Security**
2. Handling front-end calls with controllers(**Spring MVC**)
3. Making calls to the database with **MyBatis** mappers

## 2. The Front-End
Edited these templates and inserted **Thymeleaf** attributes to supply the back-end data and functionality described by the following individual page requirements:

1. Login page
2. Sign Up page
3. Home page: 

The home page is the center of the application and hosts the three required pieces of functionality(File, Notes, Credentials). The home page has a logout button that allows the user to log out of the application and keep their data private.

## 3. Testing

1. There are tests for user signup, login, and unauthorized access restrictions.
2. There are tests for note creation, viewing, editing, and deletion.
3. There are tests for credential creation, viewing, editing, and deletion.
