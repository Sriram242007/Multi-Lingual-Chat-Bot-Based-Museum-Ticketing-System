# Test / Verification Report

## Environment
- Target project: JDK 24
- Available compiler in this repair environment: JDK 21.0.11
- MySQL server: not installed
- MySQL Connector/J: not included in the supplied ZIP

JDK 24 and live MySQL could therefore not be honestly marked as verified. The complete source tree compiled successfully on JDK 21, which supports the Java 21 language features used by the project.

## Results

| Test | Expected Result | Actual Result | Status |
|---|---|---|---|
| Compile all Java sources | No compilation errors | `javac` completed successfully | PASS |
| Phase 2 | Model tests pass | All Phase 2 tests passed | PASS |
| Phase 3 FileUtil | File operations work | Passed | PASS |
| Phase 3 InputValidator test | Test should correctly verify invalid input | Existing test expects an exception from a boolean method | FAIL - TEST DEFECT |
| Phase 3 repositories | Repository operations work | Passed | PASS |
| Phase 4 authentication/exhibits/availability | Existing services work | Passed | PASS |
| Phase 4 payment | Existing simulation may succeed or fail randomly | One run can fail because of the documented 80% rate | NOT APPLICABLE |
| Phase 5 localization | Existing messages resolve | Passed after missing-key repair | PASS |
| Phase 6 chatbot | Normalization/commands/state work | Passed | PASS |
| Phase 7 end-to-end booking/receipt | Booking and receipt work when payment succeeds | Targeted verification created a receipt successfully | PASS |
| Booking persistence | Existing booking file restores records | BK10001 loaded after repository initialization | PASS |
| Invalid registration | Invalid input is rejected | Targeted verification rejected invalid registration | PASS |
| Cancellation matching | Correct date and time slot are released | Date + time matching implemented | PASS |
| Phase 8 demonstrations | Existing demonstrations run | Completed | PASS |
| Phase 9 concurrency | Existing demonstrations run | Completed selected concurrency demonstration | PASS |
| Phase 10 Java 21 features | Pattern/record demos run | Completed selected demonstration | PASS |
| MySQL JDBC connection | Live DB works | No MySQL server/driver available | NOT APPLICABLE |
| JDK 24 runtime | Application runs on JDK 24 | JDK 24 unavailable here | NOT APPLICABLE |

The existing test programs sometimes print `ALL ... TESTS PASSED` without asserting every printed condition, so this report distinguishes real verification from those summary messages.
