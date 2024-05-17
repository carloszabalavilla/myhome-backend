# CHANGELOG

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
For the
syntax: [File.md sintaxis](https://docs.github.com/es/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)

## [1.0.1] - 2024-05-17

### Fixed

- Some errors where fixed in user management
- Updated branch

## [1.0.0] - 2024-05-14

### Added

First version of the api with the following features (still not tested):

- Controllers defined and tested with tests
- Services implemented
- Repositories implemented
- Entities defined
- DTOs defined
- Mappers defined
- Exceptions defined

Next steps: configure spring security, add more features to the api, add more tests, add more documentation.

## [0.1.0] - 2024-04-24

### Added

- Integrated with client app
- New entity task added
- CorsConfig added
- Auth and user Login tested

## [0.0.7] - 2024-04-18

### Added

- New entity task added
- Exception management added
- User methods refactored
- Task methods added
  Task endpoints are not tested yet. Create a new issue to test them.

## [0.0.6] - 2024-04-16

### Fixed

- Access denied for endpoints fixed
- Tested user endpoints.
- Controller, service and user well-formed built

## [0.0.5] - 2024-04-08

### Added

- New fields to Users and some changes.
- Database Initializer is added.
- Password encoder is added.
  Still not all endpoints are tested. There is a problem with some endpoint's security.
  It is necessary to finish userDTO fields and some features as testing endpoints and making a registration confirmation
  email.
  Will be created an issue with the tasks to do.

## [0.0.4] - 2024-04-07

### Added

- New endpoints to manage users are added:
    - `GET /users` to get all users.
    - `GET /users/user` to get a user by id/email/role.
    - `POST /users/user` to create a user.
    - `PUT /users/user` to update a user.
    - `DELETE /users/user` to delete a user by id.
- New methods to manage users are added to the user service, repository, and controller classes.
- Security is added to the application using Spring Security.
- New exceptions are added.
- Email sender and user confirmation email are added.
- New application properties are added.
- New dependency added to the project.
  All endpoints aren't still tested. There is a problem with some endpoint's security.
  It is necessary to fix the security problem and test all endpoints.
  Add the collection of requests to the parent folder of the repository.

## [0.0.3] - 2024-04-06

### Fixed

- Requests are now validated before being processed.

## [0.0.2] - 2024-04-05

### Added

- User class with attributes and methods.
- User service class with methods to manage users.
- User repository class with methods to manage users.
- User controller class with methods to manage users.
- User DTO class with attributes and methods.
- User mapper class with methods to map user entities to user DTOs.
- User exception class with custom exceptions.

## [0.0.1] - 2024-04-05

### Added

- Initialized repository with Java project template.