# Debugging Report

The supplied project was repaired without redesigning its package structure or replacing its existing architecture.

| Bug / Issue | File | Root Cause | Correction | Result |
|---|---|---|---|---|
| Invalid registration input was accepted | `AuthenticationService.java` | Validation booleans were ignored | Enforced existing validation results with `InvalidInputException` | PASS |
| Password rule was inconsistent | `InputValidator.java` | Validator allowed 4 characters while existing project constant is 6 | Use `ApplicationConfig.MIN_PASSWORD_LENGTH` and update message | PASS |
| Email validation was too permissive | `InputValidator.java` | Only checked for `@` and `.` anywhere | Added bounded email-format validation | PASS |
| Localization test keys returned raw key names | `MessageProvider.java` | `app.title`, `auth.email`, `booking.success` lacked aliases | Added aliases and existing-language title messages | PASS |
| Receipt flow could not find successful payment | `PaymentService.java`, `ChatbotIntegration.java` | Payment map is keyed by transaction ID but receipt flow used booking ID | Added booking-ID lookup and used it | PASS |
| Persisted bookings were not restored | `BookingRepository.java`, `Booking.java` | Loader was a placeholder | Implemented parsing using the existing file format and persisted-booking constructor | PASS |
| Cancellation could release a slot from another date | `BookingService.java` | Matched time text only | Match both visit date and time range | PASS |
| File-based slot state was not persisted with exhibit updates | `ExhibitRepository.java` | Only `exhibits.txt` was written | Synchronize `data/time_slots.txt` in file-based application mode | PASS |

## Intentionally unchanged issues

1. The Phase 3 test has an incorrect expectation: it calls `InputValidator.validateEmail("invalid-email")` and says the call should throw, although the method's contract is `boolean`. The test file was not changed because this is a test defect.
2. `PaymentService` intentionally simulates an 80% payment success rate. Random failure was preserved rather than making the academic simulation deterministic.
3. The JDBC DAO/demo classes are academic demonstrations in the supplied architecture; they were not replaced with a new database layer.
4. Intentional threading/deadlock demonstrations were preserved.
