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
    *   **Database:** PostgreSQL (via Supabase)
    *   **Authentication:** JWT-based auth, Phone OTP, Social Login (Google, etc.)
    *   **SMS Gateway:** Twilio
    *   **File Storage:** Firebase / Cloudinary for images (worker ID, certificates)
    *   **Hosting:** Vercel (frontend), Railway / Render (backend), Supabase (database)
    *   **Realtime Chat:** Socket.IO or Firebase Realtime DB

### 2. 🛠️ DEVELOPMENT PHASE (WITH GIT INTEGRATION)

*   **Phase 1: User Registration & Email/Password Auth (`✅ COMPLETED`)**
    *   Features: Sign up/login with email and password, role selection, secure password hashing.
*   **Phase 2: Phone Number (OTP) Authentication (`IN PROGRESS`)**
    *   Modify `User` entity to allow phone-only registration.
    *   Integrate Twilio for sending SMS OTPs.
    *   Create endpoints for requesting and verifying OTPs.
*   **Phase 3: Worker Profile & Management**
    *   Fields: Skillset, Hourly rate, Experience, Location, Availability schedule.
*   **Phase 4: Client Job Posting System**
    *   Post job with: Description, Location (map integration), Required skill, Budget, Date & time window.
*   **Phase 5: Job Matchmaking Algorithm**
    *   Match workers based on: Location proximity, Availability, Skills, Rating.
*   **Phase 6: Realtime Chat System (Worker <-> Client)**
    *   Implement chat feature for job negotiation and coordination.
*   **Phase 7: Booking & Payment Flow**
    *   Confirm job, manage booking status (pending, confirmed, completed, cancelled).
    *   Integrate a Nepali payment gateway (eSewa / Khalti).
*   **Phase 8: Reviews & Ratings System**
    *   Allow both parties to review each other after job completion.
*   **Phase 9: Admin Dashboard**
    *   Manage users, verify workers, block spammers, and moderate jobs.

### 🧩 OPTIONAL ADD-ONS (FOR LATER STAGES)

*   AI-powered job suggestions
*   Worker certification upload and verification
*   Push notifications (Firebase Cloud Messaging)
*   Booking cancellation & refund logic
*   Dispute resolution system
*   GPS live tracking of hired workers
*   Video call integration for remote jobs

### 🌍 NEPALI FLAVOR + LOCALIZATION

*   Dual language support (English + Nepali)
*   Integrate eSewa / Khalti as primary payment providers.
*   Highlight local workers in a "Nearby" tab.
*   Use NPR currency format.
*   Allow users to upload citizenship/ID card for verification.
*   Referral program (e.g., earn NPR 100 per signup).

### 🤖 DEPLOYMENT

*   **Frontend:** Vercel for Next.js
*   **Backend:** Railway or Render
*   **Database:** Supabase
*   **CI/CD:** GitHub Actions to automate deployments.