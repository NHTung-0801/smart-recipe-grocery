# 🍳 Smart Recipe & Grocery (AI-Powered)

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![FastAPI](https://img.shields.io/badge/FastAPI-005571?style=for-the-badge&logo=fastapi)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

Một ứng dụng di động Offline-first hoạt động như một trợ lý bếp núc cá nhân toàn diện. Ứng dụng tích hợp Generative AI (Gemini) để sáng tạo món ăn và đi kèm một nền tảng "Social Hub" cho cộng đồng yêu ẩm thực.

## ✨ Tính năng nổi bật (Core Features)
* **🤖 AI Recipe Generator:** Tự động tạo công thức chuẩn JSON từ nguyên liệu có sẵn trong tủ lạnh qua Gemini API.
* **🛒 Smart Grocery List:** Tự động trích xuất nguyên liệu, thuật toán gộp nhóm thông minh (Smart Aggregation) và phân loại theo khu vực quầy hàng.
* **📊 Macro & Nutrition Tracking:** Tính toán Calo/Macro và theo dõi tiến độ dinh dưỡng theo mục tiêu cá nhân.
* **🌐 Culinary Social Hub:** Chia sẻ công thức lên Server (MySQL), lướt Newsfeed mượt mà (Paging 3 + Redis Cache) và "Clone" công thức của người khác về máy (Room DB).
* **📱 Cooking Mode:** Trải nghiệm nấu ăn rảnh tay, chống tắt màn hình với UI tối ưu.

## 🏗️ Kiến trúc & Công nghệ (Tech Stack)
### 1. Android Client (Thư mục `android-client`)
* **Ngôn ngữ & Kiến trúc:** Kotlin, MVVM.
* **Local Database:** Room Database (Single Source of Truth).
* **Networking & Ảnh:** Retrofit2, OkHttp, Coil.
* **Kỹ thuật:** Paging 3 (Infinite scroll), Coroutines & Flow.

### 2. Backend Service (Thư mục `fastapi-backend`)
* **Framework:** Python / FastAPI (Microservices approach).
* **Database & Cache:** MySQL, Redis.
* **Security:** JWT Authentication.

## 🚀 Hướng dẫn cài đặt (Installation)
*(Đang cập nhật - Sẽ bổ sung hướng dẫn run app Android và start server FastAPI sau)*

## 📂 Cấu trúc thư mục dự án
```text
smart-recipe-grocery/
│
├── android-client/       # Source code ứng dụng Android (Kotlin)
├── fastapi-backend/      # Source code hệ thống Server (Python)
└── README.md
