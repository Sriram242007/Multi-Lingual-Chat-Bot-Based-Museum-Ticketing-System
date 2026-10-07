# Database Documentation

## Database name
`museum_db` (MySQL)

## Tables
- **users** — email PK; stores visitor/admin identity, credentials, status, language and role-specific fields.
- **exhibits** — exhibit ID PK; stores name, description, category, fee, location and open status.
- **time_slots** — slot ID PK; `exhibit_id` FK to `exhibits`; stores date, start/end time, capacity, booked tickets and status.
- **bookings** — booking ID PK; `visitor_email` FK to `users`, `exhibit_id` FK to `exhibits`; stores visit date, time-slot text, quantity, pricing and status.
- **payments** — transaction ID PK; `booking_id` FK to `bookings`, `payer_email` FK to `users`; stores simulated payment information.
- **receipts** — receipt ID PK; `booking_id` FK to `bookings`, `transaction_id` FK to `payments`, `visitor_email` FK to `users`.

## Relationships
- `users` 1-to-many `bookings`
- `exhibits` 1-to-many `time_slots`
- `exhibits` 1-to-many `bookings`
- `bookings` 1-to-many `payments` is represented by the foreign key, although the current Java payment simulation normally creates one payment per confirmation.
- `bookings` 1-to-1 `receipts` in the supplied receipt workflow.
- `payments` 1-to-1 `receipts` in the supplied receipt workflow.

## Compatibility note
The supplied Java application currently reads `exhibits` and `time_slots` through `ExhibitRepository` when MySQL is available. User, booking and payment services continue to use the existing file/in-memory repositories in the supplied architecture. The SQL documents the existing domain model without replacing that architecture.
