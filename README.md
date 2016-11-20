# DuribonDuribon

Mobile Programming 

ChangeLog:

20161031 - TabLayout로 디자인 레이아웃 구성, Fragment 추가, 한국어 추가(다국어 지원)

20161104 - Toolbar을 개조해, 검색과 추가 옵션 기능 추가, 시간표 데이터베이스 기본 칼럼 구축 및 표 작성

20161105 - 각 Tab별로 옵션 메뉴 설정, 내부 지도UI 추가, 사용자 위치 알림UI 추가

20161114 - 외부 지도 Google Map --> TMap으로 교체, 경로 탐색 적용

20161115 - 임시 메인 화면 추가 및 Mashmallow 버전 이상 권한 부여 코드 추가, 권한 부여 코드에 추가 적용해야 할 부분 있음,,

20161118 - DuribonDuribon 아이콘 추가, 기타 버그 수정

20161120 - Tab Icon화를 위한 Toolbar Title 표시 변경, 외부 지도 내 위치 범위를 단국대학교 천안캠퍼스로 한정,,

        1118 버그 수정 사항

            --> 가로 모드 사용시, Fragment, Activity onCreate() 메소드가 다시 재호출되는 문제점:: Manifest.xml 파일 수정,,

            --> 위치 권한 부여시, 현재 시스템 종료 호출 값을 0으로 맞춘 상태:: 0 상태를 유지하기로 결정,,

            --> IntroAppActivity 잘못된 코드 구현으로 인한, Marshmallow 이하 버전에서 두리번 두리번 앱 오동작 오류 수정,,

        
        현재 발견된 버그와 개선해야할 사항

            --> 아직 내부 지도의 구현이 되지 않음,,

            --> 5" 이상 디바이스에서 Toolbar에 구현된 검색바 디자인이 제대로 구현되지 않는 버그 
