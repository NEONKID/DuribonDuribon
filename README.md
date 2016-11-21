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

20161121 - Tab 임시 아이콘화, 외부 지도 검색 범위를 제한하지 않되, 경로 탐색 범위를 단국대학교 천안캠퍼스로 한정,,

        1121 개선 사항

            --> App Loading 속도 개선

            --> 경로 탐색 없이 사용자 위치 지정 버튼 Action 방지

            --> 죽전캠퍼스를 포함하여  다른 위치가 탐색될 경우, 경로 탐색 제한

            --> Android Support Library v21 (Supoorted upper Android 5.0) 디자인에서 상단바 색깔 추가

            --> Tab의 구분을 String --> Icon으로 변경

            --> (*코드 변경 사항) TabPagerAdapter 클래스를 TabsFragment 클래스에서 분리 

        
        현재 발견된 버그와 개선해야할 사항

            --> 아직 내부 지도의 구현이 되지 않음,,

            --> 5" 이상 디바이스에서 Toolbar에 구현된 검색바 디자인이 제대로 구현되지 않는 버그 

            --> 경로 탐색 중, 사용자 위치 지정이 일부 나타나지 않는 버그
