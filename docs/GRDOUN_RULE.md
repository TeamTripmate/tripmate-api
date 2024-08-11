# Scrum
- PR: 무조건 3일 안에 리뷰
- 미팅: 화요일 오후 9시 회의 활용 (끝나고 리뷰, 위클리?)
- 생각나면 백로그 올리고, 마일스톤만 잘 지키기

# Git Convention
### Branch
> Git flow
- main: 메인 배포 용도
- dev: 개발 용도
- feat/<이슈번호-이슈 이름>: 기능 개발 용도
    - feat/1-spring-boot-configuration
- fix/<이슈번호-이슈 이름>:
    - fix/1-spring-boot-build-error

### Commits
> [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

- feat, refactor, chore, style, fix, docs, test, ci/cd

```text
[#이슈번호] [TAG] 제목
- 내용
- 내용

ex)
[#1] [feat] spring boot 필터 추가
- MultipartFilter 추가
- 내용
```

### Issues
> 필요하면 추가 예정
- **Feature**
    - Background, Tasks, Acceptance Criteria, References
- **Bug**
    - Background(Expected Behavior, Current Behavior), ...

### PR
-  Issue number and link, Summary, Describe your changes, 고민점들

### Label
> 일단 기본 라벨을 활용하되, 필요하면 늘림
- need-review

# Code Convention
intellij 스타일 정의 ex. 탭은 스페이스 4개, 몇 자 이상 넘어가면 줄 넘김
- 구글(?)

# Build&CI/CD
- Test 임계치 추후 논의
- Github Action 추후 논의

# CASE Tools
- Github
- Draw-io
- ERD Cloud
- Docker