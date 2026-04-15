<!--
Sync Impact Report
- Version change: 1.0.0 → 1.1.0
- Modified principles:
    - IV. API-First Contract & Swagger Documentation → IV. API-First Contract, Versioning & Swagger Documentation
- Added sections:
    - Principle VI. API Versioning & Pagination Standards
- Removed sections:
    - None
- Templates requiring updates:
    - ✅ updated: .specify/templates/plan-template.md
    - ✅ updated: .specify/templates/spec-template.md
    - ✅ updated: .specify/templates/tasks-template.md
    - ✅ reviewed (no changes required): .github/prompts/speckit.constitution.prompt.md
    - ✅ reviewed (no changes required): .github/prompts/speckit.plan.prompt.md
    - ✅ reviewed (no changes required): .github/prompts/speckit.specify.prompt.md
    - ✅ reviewed (no changes required): .github/prompts/speckit.tasks.prompt.md
- Follow-up TODOs:
    - None
-->

# DSW01-Practica02 Constitution

## Core Principles

### I. Spring Boot 3 + Java 17 Baseline

All backend services MUST run on Java 17 and Spring Boot 3.x. New features MUST be implemented
within the Spring ecosystem already used by the project (Spring Web, Spring Data, Spring Security,
and springdoc where applicable) unless an exception is explicitly approved in writing.
Rationale: a single runtime baseline reduces integration risk, build drift, and upgrade complexity.

### II. API Security with HTTP Basic (NON-NEGOTIABLE)

All non-public endpoints MUST require HTTP Basic authentication. The default local credentials are
`admin` / `admin123` and MUST be treated as development defaults only; production credentials MUST
be injected via environment variables or secret managers. Security configuration MUST explicitly
document any unauthenticated endpoints (for example Swagger and health checks).
Rationale: enforcing authentication by default minimizes accidental exposure of backend capabilities.

### III. PostgreSQL as System of Record via Docker

Persistent application data MUST be stored in PostgreSQL. Local and CI environments MUST run
PostgreSQL through Docker (or Docker Compose) with configuration supplied through environment
variables. Application code MUST not depend on in-memory databases for production behavior.
Rationale: a single persistent engine across environments improves parity and prevents drift.

### IV. API-First Contract, Versioning & Swagger Documentation

Every exposed REST endpoint MUST be represented in OpenAPI documentation and visible through
Swagger UI. Endpoints MUST be versioned (for example `/api/v1/...`) and versioning MUST be
documented in the OpenAPI contract. Contract changes MUST be reflected in docs in the same change
set as implementation. Public request/response models MUST be explicit and validated.
Rationale: synchronized API contracts reduce client breakage and speed onboarding.

### V. Test Discipline & Quality Gates

Every feature change MUST include automated tests at the appropriate level (unit, integration, or
controller tests). Security rules, DB interactions, and API contracts MUST be covered by at least one
automated test path before merge. A change that lowers coverage in touched modules MUST include a
written justification in the plan.
Rationale: backend reliability depends on repeatable verification of behavior and integrations.

### VI. API Versioning & Pagination Standards

All list-style endpoints MUST implement pagination with explicit request parameters (page, size)
and MUST return pagination metadata in the response. Default and maximum page sizes MUST be
documented in the OpenAPI contract. Breaking API changes MUST trigger a new versioned route.
Rationale: versioning and pagination prevent unbounded payloads and protect client stability.

## Stack & Operational Constraints

- Runtime MUST remain Java 17 and Spring Boot 3.x.
- Authentication mechanism MUST remain HTTP Basic unless this constitution is amended.
- Default local basic-auth credentials are `admin` / `admin123`.
- Data layer MUST target PostgreSQL and be configurable by environment variables.
- Docker artifacts (Dockerfile and/or Compose) MUST be maintained for reproducible local execution.
- Swagger/OpenAPI endpoints MUST remain accessible for API discovery.
- Public API routes MUST be versioned.
- List endpoints MUST implement and document pagination defaults and limits.

## Delivery Workflow & Compliance Gates

1. `speckit.specify` outputs MUST include explicit security, PostgreSQL, Docker, and API-doc impacts.
2. `speckit.plan` MUST pass Constitution Check gates before implementation starts.
3. `speckit.tasks` MUST include foundational tasks for security, database configuration, and docs.
4. Pull requests MUST include:
    - evidence of test execution,
    - confirmation that Swagger docs are updated when APIs change,
    - confirmation that Docker-based PostgreSQL configuration still works,
    - confirmation that API versioning and pagination are applied where relevant.
5. Any exception to principles MUST be documented in the plan's complexity tracking section and
   approved before merge.

## Governance

This constitution supersedes conflicting project conventions and templates. Amendments require a
documented rationale, impact assessment on templates, and a version update following semantic
rules below.

Versioning policy:

- MAJOR: remove or redefine a core principle in a backward-incompatible way.
- MINOR: add a new principle/section or materially expand governance requirements.
- PATCH: clarify wording, fix ambiguity, or apply non-semantic editorial updates.

Compliance review expectations:

- Every feature plan MUST include a constitution compliance check.
- Every review MUST verify compliance before approval.
- Non-compliance MUST block merge unless an approved exception is documented.

**Version**: 1.1.0 | **Ratified**: 2026-02-25 | **Last Amended**: 2026-03-05
