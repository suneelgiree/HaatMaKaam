# HaatMaKaam - Decentralized Service Marketplace

"HaatMaKaam" is a decentralized, hourly-based service marketplace for everyday skilled laborers in Nepal (e.g., plumbers, electricians, carpenters, masons, drivers, cleaners, tutors, tailors, mechanics, photographers, cooks, etc.). Users (clients) can post needs, and service providers (workers) can register, get verified, and take up jobs based on proximity and availability.

---

## 🚧 STEP-BY-STEP IMPLEMENTATION

### 1. 🧠 PLANNING PHASE

*   **DB Schema:** Users, Workers, Jobs, Chats, Bookings, Reviews, Wallets, Availability, and Notifications.
*   **Roles:** Admin, Client, Worker.
*   **Tech Stack:**
    *   **Frontend:** React.js (Next.js)
    *   **Backend:** Java (Spring Boot)
    *   **Database:** PostgreSQL
    *   **Authentication:** JWT-based auth + Google login
    *   **File Storage:** Firebase / Cloudinary for images (worker ID, certificates)
    *   **Hosting:** Vercel (frontend), Render / Railway / Supabase (backend/db)
    *   **Realtime Chat:** Socket.IO or Firebase Realtime DB

### 2. 🛠️ DEVELOPMENT PHASE (WITH GIT INTEGRATION)

*   **Phase 1: User & Worker Authentication System**
    *   Features: Sign up/login, role selection, profile completion.
*   **Phase 2: Worker Profile Creation**
    *   Fields: Skillset, Hourly rate, Experience, Location, Availability schedule.
*   **Phase 3: Client Job Posting System**
    *   Post job with: Description, Location (Google Maps integration optional), Required skill, Budget & time, Date & time window.
*   **Phase 4: Job Matchmaking Algorithm**
    *   Match workers based on: Location proximity, Availability, Skills match, Rating threshold.
*   **Phase 5: Chat System (Worker <-> Client)**
    *   Realtime chat feature with optional file/image sending.
*   **Phase 6: Booking & Payment Flow**
    *   Confirm job, manage booking status, and integrate payment flow (Cash / Khalti / eSewa).
*   **Phase 7: Reviews & Ratings System**
    *   Allow both parties to review each other after job completion.
*   **Phase 8: Admin Dashboard**
    *   Manage users, block spammers, and moderate jobs.

### 🧩 OPTIONAL ADD-ONS (FOR LATER STAGES)

*   AI-powered job suggestions
*   Worker certification upload
*   Push notifications (PWA or Firebase Cloud Messaging)
*   Booking cancellation & refund logic
*   Nepali language translation toggle
*   Dispute resolution system
*   Subscription plan for premium workers
*   GPS live tracking of hired workers
*   Video call integration for remote jobs

### 🌍 NEPALI FLAVOR + LOCALIZATION

*   Dual language support (English + Nepali)
*   Integrate eSewa / Khalti as payment providers
*   Highlight local workers in a "Nearby" tab
*   Use NPR currency format
*   Allow users to upload citizenship/ID card for verification
*   Referral program (e.g., earn NPR 100 per signup)

### 🤖 DEPLOYMENT

*   **Frontend:** Vercel for Next.js
*   **Backend:** Supabase or Railway
*   **CI/CD:** GitHub Actions
