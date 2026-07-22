## ☕ Backend Tech Stack & Features

Java 21과 Spring Boot 기반으로 구축된 백엔드 서버의 기술 스택 및 주요 구현 기능입니다.

### 🛠 Tech Stack (Versions)

| Category | Technology | Version | Description |
| :--- | :--- | :--- | :--- |
| **Core** | Java (JDK) | 21 | 모던 자바 플랫폼 |
| **Framework** | Spring Boot | 4.0.6 | 엔터프라이즈 급 웹 애플리케이션 프레임워크 |
| **ORM / DB** | Spring Data JPA / MariaDB | Boot 연동 / 3.x | 객체-관계 매핑 및 관계형 데이터베이스 |
| **Query** | Querydsl | 5.0.0 (jakarta) | 타입 안전한 동적 쿼리 및 Q클래스 생성 |
| **Security** | Spring Security & JWT | Boot 연동 / 0.11.5 | 인증/인가 및 토큰 기반 보안 처리 |
| **Validation** | Spring Boot Validation | Boot 연동 | 입력값 검증 및 데이터 무결성 보장 |
| **Build Tool** | Gradle | Kotlin/Groovy | 빌드 및 의존성 관리 도구 |

---

### ✨ Key Features & Implementation

* **현대적인 자바 및 스프링 환경 (Java 21 & Spring Boot 4.0.6)**
    * 최신 Java LTS 버전의 성능 최적화 기능 활용 및 Spring Boot 기반 빠른 서버 구성
* **데이터 영속성 및 동적 쿼리 (Spring Data JPA & Querydsl)**
    * JPA를 활용한 객체 중심의 데이터베이스 설계 및 관리
    * Querydsl(`jakarta` 네임스페이스)을 적용하여 복잡한 검색 및 동적 쿼리를 타입 안전(Type-Safe)하게 구현
* **보안 및 인증 시스템 (Spring Security & JWT)**
    * Spring Security와 JJWT 라이브러리를 활용한 Stateless 인증/인가 구조 구현 (토큰 기반 로그인 처리)
* **데이터 검증 및 안정성 (Validation)**
    * DTO 레벨의 애노테이션 기반 검증을 통해 잘못된 요청 데이터 사전 차단
* **데이터베이스 및 테스트 환경**
    * 프로덕션 환경의 `MariaDB` 연동 및 테스트 환경을 위한 `H2` 인메모리 DB 구성