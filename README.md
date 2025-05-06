MyMedia 🌐💬

MyMedia is a simple and clean social media platform where users can register, log in, create posts, and interact with each other by liking content. Each user has a personalized dashboard for managing their activity. The app is built using Java Spring Boot on the backend and HTML/CSS/JS on the frontend.

🌟 Features

For Users:

✅ Register and log in securely

📝 Create, update, and delete posts

🖼️ Attach image URLs to posts

👍 Like or unlike other users’ posts

💬 Comments other Users' post

🧑‍💻 View your own posts in a profile/dashboard

🔍 Explore posts by all users

🔐 Secure sessions using JWT

📥 Log out anytime

🔐 Security:

🔹Role-based access control (ready for admin/mod support)

🔹Spring Security and JWT token-based authentication

🔹Passwords hashed with SHA-1 for security

Input validation and error handling

🛠️ Tech Stack

🖥️ Frontend

🔹 HTML

🔹CSS

🔹JavaScript

Bootstrap (for UI styling)

🔹🔙 Backend

🔹Java Spring Boot

🔹Spring Security + JWT (Authentication & Authorization)

🔹REST APIs for all core features

🗄️ Database

🔹MySQL

🔹Spring Data JPA

🔹UUIDs used for users

Tables: Users, Posts, Likes, Comments

🔄 System Modules

📦 Post System:

🔹Full CRUD for user-generated posts

❤️ Like System:

🔹Toggle like/unlike on posts
🔹Real-time like counts

👤 User System:

🔹Register with full name, username, email, password

🔹JWT-secured login and access

🔹View your personal post list

🧩 Architecture Overview

Layered backend structure:

🔹Controller → Service → Repository → Database

🔹DTOs used for clean API interaction

🔹JWT filter for route protection

📄 MyMedia Documentation & Diagrams

📘 API Documentation

[View Document](https://docs.google.com/document/d/10mvREV788nlk1FDUE6_UJDKBmeJ38sO78OvCEzkH7mE/edit?usp=sharing)  

🏛️ Architecture Diagram

[View Document](https://docs.google.com/document/d/1XpRz506_RmZErh7j4WTksIsSbCpeNb4Fcl8RM4-c1no/edit?usp=sharing)  

🗂️ ER Diagram

[View Document](https://docs.google.com/document/d/14y6QYdzFT3BTIuudWl-9N8jXcSFxYVlFPFOcqKSP25Y/edit?usp=sharing) 

 Postman API Screenshot
![Screenshot 2025-05-06 091325](https://github.com/user-attachments/assets/a4f7c066-c03f-4ad0-895e-1a237a62f63d)
