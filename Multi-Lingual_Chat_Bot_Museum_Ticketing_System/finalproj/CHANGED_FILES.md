# Changed Files

Existing source files modified:

- `src/museum/util/InputValidator.java` — corrected email validation and aligned password validation with the existing six-character project rule.
- `src/museum/service/AuthenticationService.java` — enforced the existing validation results.
- `src/museum/localization/MessageProvider.java` — added missing aliases/messages used by the existing tests.
- `src/museum/model/Booking.java` — added a constructor for restoring persisted bookings with their original ID/timestamp.
- `src/museum/repository/BookingRepository.java` — implemented booking-file loading and prevented duplicate visitor-index entries.
- `src/museum/service/PaymentService.java` — added lookup by booking ID for the existing receipt workflow.
- `src/museum/chatbot/ChatbotIntegration.java` — corrected payment lookup during receipt generation.
- `src/museum/service/BookingService.java` — cancellation now matches both visit date and time.
- `src/museum/repository/ExhibitRepository.java` — synchronizes file-based time-slot data when exhibits are persisted.

New artifacts:

- `database/museum_db.sql`
- `database/sample_data.sql`
- `DATABASE_DOCUMENTATION.md`
- `DATABASE_SETUP_GUIDE.txt`
- `DEBUG_REPORT.md`
- `TEST_REPORT.md`
- `CHANGED_FILES.md`

No existing package/class was renamed, moved or removed.
