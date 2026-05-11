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
com.example.smartrecipe
│
├── core/                               # TẦNG CỐT LÕI: Chứa mã nguồn dùng chung cho toàn dự án
│   ├── base/                           # Các class cha để tái sử dụng logic
│   │   ├── BaseActivity.kt             # Xử lý các tác vụ chung của Activity (VD: setup ngôn ngữ, theme)
│   │   ├── BaseFragment.kt             # Xử lý binding, vòng đời chung của UI
│   │   └── BaseViewModel.kt            # Xử lý CoroutineExceptionHandler chung, quản lý state cơ bản
│   │
│   ├── utils/                          # Các hàm tiện ích, công cụ độc lập
│   │   ├── DateUtils.kt                # Format ngày tháng cho Lịch thực đơn, Newsfeed
│   │   ├── PriceFormatter.kt           # Định dạng tiền tệ cho tính năng Dự toán ngân sách (Budget)
│   │   ├── ShareUtils.kt               # Format danh sách đi chợ thành text (bullet points) để gửi Zalo/SMS
│   │   └── NutritionCalculator.kt      # Công thức toán học tính BMI, TDEE, % Macro
│   │
│   ├── extensions/                     # Kotlin Extensions giúp code ngắn gọn hơn
│   │   ├── ViewExt.kt                  # Các hàm mở rộng cho View (VD: view.visible(), view.gone())
│   │   ├── StringExt.kt                # VD: Xử lý chuỗi JSON từ Gemini AI trả về
│   │   └── ContextExt.kt               # Hàm gọi nhanh Toast, SnackBar
│   │
│   └── workers/                        # WorkManager: Xử lý các tác vụ chạy ngầm (Background Tasks)
│       ├── SyncDataWorker.kt           # Tự động đồng bộ dữ liệu (Local Room -> Backend) khi có mạng
│       └── OfflinePublishWorker.kt     # Hàng đợi các bài đăng/thả tim lúc offline, chờ đẩy lên server
│
├── di/                                 # DEPENDENCY INJECTION: Nơi khởi tạo và cung cấp các module
│   ├── NetworkModule.kt                # Cung cấp Retrofit, OkHttpClient (giao tiếp với FastAPI)
│   ├── DatabaseModule.kt               # Cung cấp Room Database instance và các DAO
│   ├── RepositoryModule.kt             # Cung cấp các implementation của Repository cho UseCase
│   └── DataStoreModule.kt              # Cung cấp instance của Jetpack DataStore (lưu Token, Setting)
│
├── data/                               # TẦNG DỮ LIỆU: Quản lý nguồn dữ liệu (Local & Remote)
│   ├── local/                          # Dữ liệu Offline (Single Source of Truth)
│   │   ├── dao/                        # Data Access Objects (Thực thi câu lệnh SQL)
│   │   │   ├── RecipeDao.kt            # CRUD Công thức, tìm kiếm theo Tag
│   │   │   ├── GroceryDao.kt           # Quản lý danh sách đi chợ, xử lý thao tác Swipe-to-delete
│   │   │   ├── JournalDao.kt           # Lưu trữ ghi chú nấu ăn và lịch sử Macro Tracking
│   │   │   └── SocialDao.kt            # Cache dữ liệu Newsfeed (hỗ trợ Paging 3 Offline)
│   │   ├── entity/                     # Cấu trúc bảng trong Room DB
│   │   │   ├── RecipeEntity.kt         # Bảng Công thức (có liên kết 1-N với IngredientEntity)
│   │   │   ├── TagEntity.kt            # Bảng Tag (#GiảmCân, #ĂnChay)
│   │   │   └── ...
│   │   ├── datastore/                  # Quản lý Preferences DataStore
│   │   │   └── AppPreferences.kt       # Lưu JWT Token, Theme Preference, Mục tiêu Calo/ngày
│   │   └── AppDatabase.kt              # Khai báo các Entity và version của Room Database
│   │
│   ├── remote/                         # Dữ liệu Online (Giao tiếp với Server)
│   │   ├── api/                        # Các interface định nghĩa endpoint
│   │   │   ├── AuthApi.kt              # Đăng ký, Đăng nhập
│   │   │   ├── SocialApi.kt            # Fetch Newsfeed, Like, Comment, Clone recipe
│   │   │   └── GeminiApi.kt            # Gọi AI tạo công thức từ nguyên liệu dư thừa
│   │   └── dto/                        # Data Transfer Objects (Hứng dữ liệu JSON từ Server)
│   │       ├── RecipeDto.kt            # Dữ liệu công thức từ Backend
│   │       └── GeminiResponseDto.kt    # Cấu trúc JSON chuẩn từ AI
│   │
│   ├── mapper/                         # Chuyển đổi dữ liệu giữa các tầng (Tránh rò rỉ dữ liệu DB/API lên UI)
│   │   ├── RecipeMapper.kt             # Map RecipeEntity (Room) hoặc RecipeDto (API) -> Recipe (Domain)
│   │   └── GroceryMapper.kt            # Map Entity -> Model chuẩn
│   │
│   └── repository/                     # Nơi quyết định lấy dữ liệu từ đâu (Cache hay gọi API)
│       ├── RecipeRepositoryImpl.kt     # Quản lý luồng dữ liệu công thức
│       └── SyncRepositoryImpl.kt       # Xử lý logic đồng bộ dữ liệu giữa Room và MySQL
│
├── domain/                             # TẦNG NGHIỆP VỤ: Chứa logic cốt lõi, hoàn toàn độc lập với Android framework
│   ├── model/                          # Cấu trúc dữ liệu thuần túy (Dùng cho UI và UseCase)
│   │   ├── Recipe.kt                   # Công thức hoàn chỉnh
│   │   ├── GroceryItem.kt              # Món đồ cần mua
│   │   └── UserProfile.kt              # Thông tin user
│   │
│   ├── repository/                     # Các Interface định nghĩa hàm (Data layer sẽ implement)
│   │   ├── IRecipeRepository.kt        
│   │   └── IGroceryRepository.kt       
│   │
│   └── usecase/                        # Chia nhỏ từng logic phức tạp thành các class riêng biệt
│       ├── auth/                       # Đăng ký, Đăng nhập, Đăng xuất, Phân quyền
│       ├── recipe/                     
│       │   ├── RecalculatePortionUseCase.kt # Tự động tính lại định lượng khi đổi khẩu phần ăn
│       │   └── ManageTagsUseCase.kt         # Gợi ý và gán thẻ Tag thông minh
│       ├── grocery/                    
│       │   ├── ExtractIngredientsUseCase.kt # Bóc tách nguyên liệu từ công thức vào danh sách đi chợ
│       │   ├── AggregateGroceryUseCase.kt   # Gộp nguyên liệu trùng lặp (Cộng dồn định lượng)
│       │   ├── CategorizeAisleUseCase.kt    # Phân loại theo quầy hàng (Rau củ, Thịt cá)
│       │   ├── CheckPantryUseCase.kt        # Đối chiếu tủ lạnh, tự động gạch bỏ đồ đã có
│       │   └── EstimateBudgetUseCase.kt     # Dự toán ngân sách dựa trên giá nguyên liệu
│       ├── nutrition/                  
│       │   ├── TrackDailyMacroUseCase.kt    # Cập nhật lượng Calo/Macro đã nạp vào
│       │   └── SetNutritionGoalUseCase.kt   # Thiết lập mục tiêu dinh dưỡng hàng ngày
│       ├── planner/                    
│       │   └── GenerateWeeklyPlanUseCase.kt # Sắp xếp và lấy danh sách thực đơn theo tuần
│       ├── journal/                    
│       │   ├── AddIterationNoteUseCase.kt   # Lưu ghi chú tinh chỉnh (VD: Giảm 1 thìa đường)
│       │   └── SaveCookingPhotoUseCase.kt   # Lưu đường dẫn ảnh thực tế của người dùng
│       ├── ai/                         
│       │   ├── GenerateZeroWasteRecipe.kt   # Giải cứu tủ lạnh: Tạo công thức từ đồ dư thừa (gọi Gemini API)
│       │   └── ParseGeminiJsonUseCase.kt    # Bóc tách JSON từ AI thành Model Recipe chuẩn
│       └── sync/                       
│           └── CloneRecipeUseCase.kt        # Tải công thức từ Server về lưu vào Local Room DB
│
└── ui/                                 # TẦNG GIAO DIỆN (MVVM): Xử lý hiển thị và tương tác người dùng
    ├── nav/                            # Quản lý điều hướng toàn ứng dụng
    │   ├── NavGraph.kt                 # Khai báo các màn hình và luồng đi
    │   └── DeepLinkHandler.kt          # Xử lý khi user click vào link công thức chia sẻ từ ngoài app
    │
    ├── common/                         # Các thành phần UI dùng chung cho nhiều màn hình
    │   ├── state/                      # Quản lý trạng thái UI (UiState: Loading, Success, Error)
    │   ├── adapters/                   # Các RecyclerView Adapter dùng chung (VD: List nguyên liệu)
    │   ├── components/                 # Custom View (Nút bấm, Loading Bar, Dialog chuẩn)
    │   └── BindingAdapters.kt          # Cấu hình load ảnh bằng Coil/Glide trực tiếp trên Layout XML
    │
    ├── auth/                           # Feature: Đăng ký, Đăng nhập, Quên mật khẩu
    ├── profile/                        # Feature: Hồ sơ cá nhân, Lịch sử hoạt động, Công thức đã lưu
    │
    ├── recipe/                         # Feature: Quản lý và thực hành nấu nướng
    │   ├── list/                       # Danh sách công thức (Kèm bộ lọc theo Tag: #GiảmCân, #ĂnChay)
    │   ├── detail/                     # Chi tiết công thức (Nơi gọi UseCase tính lại khẩu phần)
    │   ├── edit/                       # Màn hình thêm, sửa, xóa công thức (CRUD)
    │   ├── cooking_mode/               # Chế độ nấu ăn (UI tối ưu: chữ lớn, Keep Screen On, vuốt chuyển bước)
    │   └── nutrition/                  # Màn hình Progress Bar theo dõi mục tiêu Calo/Macro trong ngày
    │
    ├── grocery/                        # Feature: Đi chợ thông minh
    │   ├── list/                       # Danh sách đi chợ chính (Có vuốt để xóa, hiển thị theo quầy hàng)
    │   ├── pantry/                     # Đối chiếu tủ lạnh (Danh sách Checkbox các món đã có sẵn ở nhà)
    │   └── budget/                     # View Bottom Sheet hiển thị chi phí dự toán ngân sách
    │
    ├── planner/                        # Feature: Kế hoạch & AI
    │   ├── calendar/                   # Giao diện Lịch (Calendar) sắp xếp thực đơn trong tuần
    │   └── ai_generator/               # Màn hình nhập text nguyên liệu lặt vặt gửi cho AI
    │
    ├── journal/                        # Feature: Nhật ký bếp núc
    │   ├── notes/                      # Giao diện hiển thị/thêm ghi chú tinh chỉnh cá nhân
    │   └── gallery/                    # GridView thư viện ảnh thành phẩm người dùng đã chụp
    │
    └── social/                         # Feature: Nền tảng Giao lưu nội trợ (Social Hub)
        ├── feed/                       # Newsfeed cuộn vô cực (Tích hợp Paging 3, load ảnh bất đồng bộ)
        ├── detail/                     # Xem bài đăng của người khác (Nơi thực hiện nút Clone)
        └── publish/                    # Giao diện xác nhận chia sẻ công thức Local lên Server (Đồng bộ)
