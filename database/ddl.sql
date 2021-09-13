CREATE TABLE TB_ROLE (
	ROLE_ID	nvarchar(30)	NOT NULL,
	ROLE_NAME	nvarchar(100)	NOT NULL
);

CREATE TABLE TB_ADMIN (
	ADMIN_ID	nvarchar(100)	NOT NULL,
	ROLE_ID	nvarchar(30)	NOT NULL,
	ADMIN_PWD	nvarchar(500)	NOT NULL,
	ADMIN_NAME	nvarchar(100)	NOT NULL,
	ADMIN_GRADE	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 마스터 ID, 2 직원 ID, 3지점 ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_ADMIN', @level2type=N'COLUMN',@level2name=N'ADMIN_GRADE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_ADMIN', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_OWNER (
	OWNER_ID	nvarchar(100)	NOT NULL,
	ROLE_ID	nvarchar(30)	NOT NULL,
	BUILD_SEQ	int	NOT NULL,
	OWNER_PWD	nvarchar(500)	NOT NULL,
	OWNER_STATUS	int	NOT NULL,
	OWNER_PHONE	nvarchar(20)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	STORE_NAME	nvarchar(200)	NOT NULL,
	STORE_SORT	int	NOT NULL,
	STORE_KEYWORD	nvarchar(2000)	NOT NULL,
	STORE_TEXT	nvarchar(2000)	NOT NULL,
	MCATE_SEQ	int	NULL,
	STORE_STATUS	int	NOT NULL,
	STORE_HO	nvarchar(20)	NULL,
	STORE_FLOOR	int	NOT NULL,
	STORE_URL	nvarchar(30)	NULL,
	STORE_LINK	nvarchar(300)	NULL,
	STORE_TEL	nvarchar(20)	NOT NULL,
	OWNER_JOIN_DT	datetime	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 미승인, 2 승인' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_OWNER', @level2type=N'COLUMN',@level2name=N'OWNER_STATUS';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'기본 1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_OWNER', @level2type=N'COLUMN',@level2name=N'STORE_SORT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1숨김 2 노출' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_OWNER', @level2type=N'COLUMN',@level2name=N'STORE_STATUS';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_OWNER', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_USER (
	USER_ID	nvarchar(100)	NOT NULL,
	ROLE_ID	nvarchar(30)	NOT NULL,
	USER_NAME	nvarchar(30)	NOT NULL,
	USER_PWD	nvarchar(500)	NULL,
	USER_EMAIL	nvarchar(100)	NOT NULL,
	USER_BIRTH	nvarchar(12)	NULL,
	USER_GENDER	int	NOT NULL,
	USER_JOIN_TYPE	int	NULL,
	FILE_SEQ	int	NULL,
	USER_JOIN_DT	datetime	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'YYYY-MM-DD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER', @level2type=N'COLUMN',@level2name=N'USER_BIRTH';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 남성 2 여성' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER', @level2type=N'COLUMN',@level2name=N'USER_GENDER';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 일반가입
2 카카오
3 구글' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER', @level2type=N'COLUMN',@level2name=N'USER_JOIN_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'가입 일자' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER', @level2type=N'COLUMN',@level2name=N'USER_JOIN_DT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_SIDO (
	SIDO_SEQ	int IDENTITY(1,1)	NOT NULL,
	SIDO_NAME	nvarchar(50)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'시도 시퀀스' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_SIDO', @level2type=N'COLUMN',@level2name=N'SIDO_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'시도 명' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_SIDO', @level2type=N'COLUMN',@level2name=N'SIDO_NAME';

CREATE TABLE TB_SIGUNGU (
	SIGUNGU_SEQ	int IDENTITY(1,1)	NOT NULL,
	SIDO_SEQ	int	NOT NULL,
	SIGUNGU_NAME	nvarchar(50)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'시군구 시퀀스' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_SIGUNGU', @level2type=N'COLUMN',@level2name=N'SIGUNGU_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'시군구 명칭' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_SIGUNGU', @level2type=N'COLUMN',@level2name=N'SIDO_SEQ';

CREATE TABLE TB_DONG (
	DONG_SEQ	int IDENTITY(1,1)	NOT NULL,
	SIGUNGU_SEQ	int	NOT NULL,
	DONG_NAME	nvarchar(50)	NOT NULL
);

CREATE TABLE TB_BUILD (
	BUILD_SEQ	int IDENTITY(1,1)	NOT NULL,
	DONG_SEQ	int	NOT NULL,
	BUILD_STATUS	int	NOT NULL,
	BUILD_X	nvarchar(50)	NOT NULL,
	BUILD_Y	nvarchar(50)	NOT NULL,
	BUILD_NAME	nvarchar(50)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'초기상태는 비활성화 / 0비활성화 1 활성화' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BUILD', @level2type=N'COLUMN',@level2name=N'BUILD_STATUS';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'longitude' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BUILD', @level2type=N'COLUMN',@level2name=N'BUILD_X';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'latitude' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BUILD', @level2type=N'COLUMN',@level2name=N'BUILD_Y';

CREATE TABLE TB_FLOOR (
	FLOOR_SEQ	int IDENTITY(1,1)	NOT NULL,
	BUILD_SEQ	int	NOT NULL,
	FLOOR_TYPE	int	NOT NULL,
	FLOOR_NAME	nvarchar(10)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1지하, 2지상' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_FLOOR', @level2type=N'COLUMN',@level2name=N'FLOOR_TYPE';

CREATE TABLE TB_LCATE (
	LCATE_SEQ	int IDENTITY(1,1)	NOT NULL,
	BUILD_SEQ	int	NOT NULL,
	LCATE_NAME	nvarchar(50)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_MCATE (
	MCATE_SEQ	int IDENTITY(1,1)	NOT NULL,
	LCATE_SEQ	int	NOT NULL,
	MCATE_NAME	nvarchar(50)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_OPEN (
	OPEN_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	OPEN_DAY	nvarchar(20)	NULL,
	OPEN_TIME	nvarchar(20)	NULL,
	CLOSE_TIME	nvarchar(20)	NULL,
	ADD_DT	datetime	NULL,
	ADD_ID	nvarchar(100)	NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_BRAEK (
	BREAK_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	BREAK_DAY	nvarchar(20)	NOT NULL,
	START_TIME	nvarchar(20)	NOT NULL,
	END_TIME	nvarchar(20)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_DAY_OFF (
	DAY_OFF_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	DAY_OFF_WEEK	VARCHAR(255)	NULL,
	DAY_OF_DAY	VARCHAR(255)	NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_PRODUCT (
	PRODUCT_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	PRODUCT_NAME	nvarchar(50)	NOT NULL,
	PRODUCT_PRICE	decimal(18,2)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'점포 상품 이미지' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_PRODUCT', @level2type=N'COLUMN',@level2name=N'FILE_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_PRODUCT', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_HOLIDAY (
	HOLIDAY_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	HOLIDAY_START_DAY	nvarchar(20)	NULL,
	HOLIDAY_END_DAY	nvarchar(20)	NULL,
	HOLIDAY_TYPE	int	NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'YYYY-MM-DD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_HOLIDAY', @level2type=N'COLUMN',@level2name=N'HOLIDAY_START_DAY';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'YYYY-MM-DD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_HOLIDAY', @level2type=N'COLUMN',@level2name=N'HOLIDAY_END_DAY';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1긴급휴무, 2공휴일' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_HOLIDAY', @level2type=N'COLUMN',@level2name=N'HOLIDAY_TYPE';

CREATE TABLE TB_ADMIN_GRADE (
	ADMIN_ID	nvarchar(30)	NOT NULL,
	BUILD_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_EVENT (
	EVENT_SEQ	int IDENTITY(1,1)	NOT NULL,
	EVENT_TITLE	nvarchar(500)	NOT NULL,
	EVENT_TEXT	nvarchar(MAX)	NOT NULL,
	EVENT_PRICE	decimal(18,2)	NOT NULL,
	EVENT_SORT	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	datetime	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_EVENT', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_FILE (
	FILE_SEQ	int IDENTITY(1,1)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_FILE', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_USER_LOGIN (
	USER_ID	nvarchar(100)	NOT NULL,
	USER_LAST_LOGIN	datetime	NOT NULL
);

CREATE TABLE TB_NTC (
	NTC_SEQ	int IDENTITY(1,1)	NOT NULL,
	NTC_TITLE	nvarchar(500)	NOT NULL,
	NTC_TEXT	nvarchar(max)	NOT NULL,
	NTC_TYPE	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 APP전체 2 사장님홈페이지공지사항 3사장님회원공지사항' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_NTC', @level2type=N'COLUMN',@level2name=N'NTC_TYPE';

CREATE TABLE TB_SUGG (
	SUGG_SEQ	int IDENTITY(1,1)	NOT NULL,
	SUGG_TITLE	nvarchar(500)	NOT NULL,
	SUGG_TEXT	nvarchar(max)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_SUGG_ANS (
	SUGG_ANS_SEQ	int IDENTITY(1,1)	NOT NULL,
	SUGG_SEQ	int	NOT NULL,
	SUGG_ANS_TITLE	nvarchar(500)	NOT NULL,
	SUGG_ANS_TEXT	nvarchar(max)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_HONEYARCADE (
	HONEYARCADE_SEQ	int IDENTITY(1,1)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(200)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(200)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(200)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'N / Y' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_HONEYARCADE', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_BENEFIT (
	BENEFIT_SEQ	int IDENTITY(1,1)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(200)	NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(200)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(200)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'N/Y' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BENEFIT', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_PAY (
	PAY_SEQ	int IDENTITY(1,1)	NOT NULL,
	EVENT_SEQ	int	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	PAY_DT	datetime	NOT NULL,
	PAY_MONEY	decimal(18,2)	NOT NULL,
	PAY_TYPE	int	NOT NULL,
	EVENT_TITLE	nvarchar(500)	NOT NULL,
	EVENT_TEXT	nvarchar(MAX)	NOT NULL
);

CREATE TABLE TB_PUSH (
	PUSH_SEQ	int IDENTITY(1,1)	NOT NULL,
	PUSH_TEXT	nvarchar(500)	NOT NULL,
	PUSH_IOS_LINK	nvarchar(500)	NOT NULL,
	PUSH_ANDROID_LINK	nvarchar(500)	NOT NULL,
	PUSH_REZ_DT	nvarchar(20)	NOT NULL,
	PUSH_REZ_TIME	nvarchar(20)	NOT NULL,
	PUSH_STATUS	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 완료, 2예약대기, 3예약취소, 4삭제' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_PUSH', @level2type=N'COLUMN',@level2name=N'PUSH_STATUS';

CREATE TABLE TB_EVENT_DTL (
	EVENT_DTL_SEQ	int IDENTITY(1,1)	NOT NULL,
	EVENT_SEQ	int	NOT NULL,
	EVENT_DTL_TYPE	int	NOT NULL,
	EVENT_DTL_DAY	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEl_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 건물광고(배너), 2지역광고 (배너), 3쿠폰' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_EVENT_DTL', @level2type=N'COLUMN',@level2name=N'EVENT_DTL_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'기간' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_EVENT_DTL', @level2type=N'COLUMN',@level2name=N'EVENT_DTL_DAY';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_EVENT_DTL', @level2type=N'COLUMN',@level2name=N'DEl_FLAG';

CREATE TABLE TB_AD_REQ (
	STORE_AD_SEQ	int IDENTITY(1,1)	NOT NULL,
	PAY_DTL_SEQ	int	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	AD_REQ_TYPE	int	NOT NULL,
	AD_REQ_DAY	int	NOT NULL,
	AD_REQ_TEXT	nvarchar(max)	NOT NULL,
	AD_REQ_STATUS	int	NOT NULL,
	AD_REQ_TOTAL	int	NOT NULL,
	AD_REQ_DT	datetime	NOT NULL,
	AD_REQ_ID	nvarchar(100)	NOT NULL,
	AD_REQ_ACCEPT_DT	datetime	NULL,
	AD_REQ_ACCEPT_ID	nvarchar(100)	NULL,
	AD_REQ_REJECT_DT	datetime	NULL,
	AD_REQ_REJECT_ID	nvarchar(100)	NULL,
	AD_REQ_REJECT_REASON	nvarchar(max)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 시퀀스' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'STORE_AD_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'프로모션 결제 세부내용 시퀀스' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'PAY_DTL_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 파일' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'FILE_SEQ';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 건물광고(배너), 2지역광고 (배너), 3쿠폰' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 일자' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_DAY';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 내용' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_TEXT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 미승인 2 승인 3 요청 반려' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_STATUS';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'기본값 1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_TOTAL';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 일자' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_DT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'OWNER_ID 광고 요청 작성한 사람' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_ID';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 승인 일자' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_ACCEPT_DT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 승인한 사람' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_ACCEPT_ID';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 반려 일자' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_REJECT_DT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 반려한 사람' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_REJECT_ID';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'광고 요청 반려 사유' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'AD_REQ_REJECT_REASON';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_AD_REQ', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_PAY_DTL (
	PAY_DTL_SEQ	int IDENTITY(1,1)	NOT NULL,
	PAY_SEQ	int	NOT NULL,
	PAY_DTL_TYPE	int	NOT NULL,
	PAY_DTL_DAY	int	NOT NULL,
	PAY_DTL_USE	int	NOT NULL,
	PAY_DTL_USE_DT	datetime	NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 건물광고(배너), 2지역광고 (배너), 3쿠폰' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_PAY_DTL', @level2type=N'COLUMN',@level2name=N'PAY_DTL_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 미사용 2 사용' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_PAY_DTL', @level2type=N'COLUMN',@level2name=N'PAY_DTL_USE';

CREATE TABLE TB_FILE_DTL (
	FILE_DTL_SEQ	int IDENTITY(1,1)	NOT NULL,
	FILE_SEQ	int	NOT NULL,
	FILE_TYPE	int	NOT NULL,
	FILE_DTL_PATH	nvarchar(500)	NOT NULL,
	FILE_DTL_DESC	nvarchar(200)	NOT NULL,
	FILE_DTL_ORIGIN	nvarchar(200)	NOT NULL,
	FILE_DTL_EXT	nvarchar(10)	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	FILE_DTL_URL_PATH	 nvarchar(200)	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_FILE_DTL', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_USER_DEVICE (
	USER_DEVICE_SEQ	int IDENTITY(1,1)	NOT NULL,
	USER_ID	nvarchar(100)	NOT NULL,
	USER_DEVICE_TYPE	nvarchar(30)	NOT NULL,
	USER_DEVICE_OS	int	NOT NULL,
	USER_DEVICE_TOKEN	nvarchar(500)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 스마트폰, 2 태블릿, 3기타' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER_DEVICE', @level2type=N'COLUMN',@level2name=N'USER_DEVICE_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 ANDROID , 2iOS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER_DEVICE', @level2type=N'COLUMN',@level2name=N'USER_DEVICE_OS';

CREATE TABLE TB_BANNER (
	BANNER_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	AD_TYPE	int	NOT NULL,
	BANNER_URL	nvarchar(1000)	NULL,
	BANNER_IMG	int	NULL,
	BANNER_MAIN_IMG	int	NULL,
	BANNER_TYPE	int	NOT NULL,
	BANNER_START_DAY	datetime	NOT NULL,
	BANNER_END_DAY	datetime	NOT NULL,
	BANNER_SORT	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 외부점포, 2 허니아케이드 제휴 점포' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'AD_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'FILE_SEQ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'BANNER_IMG';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'FILE_SEQ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'BANNER_MAIN_IMG';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 건물광고(배너), 2지역광고 (배너)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'BANNER_TYPE';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'9999까지' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'BANNER_SORT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_BANNER', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_BANNER_AREA (
	BANNER_SEQ	int IDENTITY(1,1)	NOT NULL,
	DONG_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_COUPON (
	COUPON_SEQ	int IDENTITY(1,1)	NOT NULL,
	OWNER_ID	nvarchar(100)	NOT NULL,
	COUPON_IMG	int	NOT NULL,
	COUPON_START_DAY	datetime	NOT NULL,
	COUPON_END_DAY	datetime	NOT NULL,
	COUPON_SORT	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'9999까지' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_COUPON', @level2type=N'COLUMN',@level2name=N'COUPON_SORT';

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'노출여부 Y/N' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_COUPON', @level2type=N'COLUMN',@level2name=N'DEL_FLAG';

CREATE TABLE TB_COUPON_BUILD (
	COUPON_SEQ	int IDENTITY(1,1)	NOT NULL,
	BUILD_SEQ	int	NOT NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NULL
);

CREATE TABLE TB_COUPON_DTL (
	COUPON_DTL_SEQ	int IDENTITY(1,1)	NOT NULL,
	COUPON_SEQ	int	NOT NULL,
	COUPON_DTL_TEXT	nvarchar(500)	NULL,
	ADD_DT	datetime	NOT NULL,
	ADD_ID	nvarchar(100)	NOT NULL,
	MOD_DT	datetime	NULL,
	MOD_ID	nvarchar(100)	NULL,
	DEL_DT	datetime	NULL,
	DEL_ID	nvarchar(100)	NULL,
	DEL_FLAG	nvarchar(1)	NOT NULL
);

CREATE TABLE TB_USER_COUPON (
	USER_ID	nvarchar(30)	NOT NULL,
	COUPON_DTL_SEQ	int	NOT NULL,
	COUPON_DOWN_DT	datetime	NOT NULL,
	COUPON_USED_DT	datetime	NOT NULL,
	COUPON_USED_FLAG	nvarchar(1)	NOT NULL,
	COUPON_START_DAY	datetime	NOT NULL,
	COUPON_END_DAY	datetime	NOT NULL
);

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'N 미사용, Y 사용' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TB_USER_COUPON', @level2type=N'COLUMN',@level2name=N'COUPON_USED_FLAG';



ALTER TABLE HoneyArcade.dbo.TB_ROLE ADD CONSTRAINT TB_ROLE_PK PRIMARY KEY (ROLE_ID);

ALTER TABLE HoneyArcade.dbo.TB_ADMIN ADD CONSTRAINT TB_ADMIN_PK PRIMARY KEY (ADMIN_ID);

ALTER TABLE HoneyArcade.dbo.TB_ADMIN ADD CONSTRAINT TB_ADMIN_FK FOREIGN KEY (ROLE_ID) REFERENCES HoneyArcade.dbo.TB_ROLE(ROLE_ID);

ALTER TABLE HoneyArcade.dbo.TB_ADMIN_GRADE ADD CONSTRAINT TB_ADMIN_GRADE_PK PRIMARY KEY (ADMIN_ID,BUILD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_USER ADD CONSTRAINT TB_USER_PK PRIMARY KEY (USER_ID);

ALTER TABLE HoneyArcade.dbo.TB_USER ADD CONSTRAINT TB_USER_FK FOREIGN KEY (ROLE_ID) REFERENCES HoneyArcade.dbo.TB_ROLE(ROLE_ID);

ALTER TABLE HoneyArcade.dbo.TB_USER_DEVICE ADD CONSTRAINT TB_USER_DEVICE_PK PRIMARY KEY (USER_DEVICE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_USER_DEVICE ADD CONSTRAINT TB_USER_DEVICE_FK FOREIGN KEY (USER_ID) REFERENCES HoneyArcade.dbo.TB_USER(USER_ID);

ALTER TABLE HoneyArcade.dbo.TB_OWNER ADD CONSTRAINT TB_OWNER_PK PRIMARY KEY (OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_OWNER ADD CONSTRAINT TB_OWNER_FK FOREIGN KEY (ROLE_ID) REFERENCES HoneyArcade.dbo.TB_ROLE(ROLE_ID);

ALTER TABLE HoneyArcade.dbo.TB_OPEN ADD CONSTRAINT TB_OPEN_PK PRIMARY KEY (OPEN_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_OPEN ADD CONSTRAINT TB_OPEN_FK FOREIGN KEY (OWNER_ID) REFERENCES HoneyArcade.dbo.TB_OWNER(OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_BRAEK ADD CONSTRAINT TB_BRAEK_PK PRIMARY KEY (BREAK_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_BRAEK ADD CONSTRAINT TB_BRAEK_FK FOREIGN KEY (OWNER_ID) REFERENCES HoneyArcade.dbo.TB_OWNER(OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_PRODUCT ADD CONSTRAINT TB_PRODUCT_PK PRIMARY KEY (PRODUCT_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_PRODUCT ADD CONSTRAINT TB_PRODUCT_FK FOREIGN KEY (OWNER_ID) REFERENCES HoneyArcade.dbo.TB_OWNER(OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_DAY_OFF ADD CONSTRAINT TB_DAY_OFF_PK PRIMARY KEY (DAY_OFF_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_DAY_OFF ADD CONSTRAINT TB_DAY_OFF_FK FOREIGN KEY (OWNER_ID) REFERENCES HoneyArcade.dbo.TB_OWNER(OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_HOLIDAY ADD CONSTRAINT TB_HOLIDAY_PK PRIMARY KEY (HOLIDAY_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_HOLIDAY ADD CONSTRAINT TB_HOLIDAY_FK FOREIGN KEY (OWNER_ID) REFERENCES HoneyArcade.dbo.TB_OWNER(OWNER_ID);

ALTER TABLE HoneyArcade.dbo.TB_USER_COUPON ADD CONSTRAINT TB_USER_COUPON_PK PRIMARY KEY (USER_ID,COUPON_DTL_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_PUSH ADD CONSTRAINT TB_PUSH_PK PRIMARY KEY (PUSH_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_HONEYARCADE ADD CONSTRAINT TB_HONEYARCADE_PK PRIMARY KEY (HONEYARCADE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_BENEFIT ADD CONSTRAINT TB_BENEFIT_PK PRIMARY KEY (BENEFIT_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_FILE ADD CONSTRAINT TB_FILE_PK PRIMARY KEY (FILE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_FILE_DTL ADD CONSTRAINT TB_FILE_DTL_PK PRIMARY KEY (FILE_DTL_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_FILE_DTL ADD CONSTRAINT TB_FILE_DTL_FK FOREIGN KEY (FILE_SEQ) REFERENCES HoneyArcade.dbo.TB_FILE(FILE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SUGG ADD CONSTRAINT TB_SUGG_PK PRIMARY KEY (SUGG_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SUGG_ANS ADD CONSTRAINT TB_SUGG_ANS_PK PRIMARY KEY (SUGG_ANS_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SUGG_ANS ADD CONSTRAINT TB_SUGG_ANS_FK FOREIGN KEY (SUGG_SEQ) REFERENCES HoneyArcade.dbo.TB_SUGG(SUGG_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_NTC ADD CONSTRAINT TB_NTC_PK PRIMARY KEY (NTC_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_BUILD ADD CONSTRAINT TB_BUILD_PK PRIMARY KEY (BUILD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_LCATE ADD CONSTRAINT TB_LCATE_PK PRIMARY KEY (LCATE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_LCATE ADD CONSTRAINT TB_LCATE_FK FOREIGN KEY (BUILD_SEQ) REFERENCES HoneyArcade.dbo.TB_BUILD(BUILD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_MCATE ADD CONSTRAINT TB_MCATE_PK PRIMARY KEY (MCATE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_MCATE ADD CONSTRAINT TB_MCATE_FK FOREIGN KEY (LCATE_SEQ) REFERENCES HoneyArcade.dbo.TB_LCATE(LCATE_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_FLOOR ADD CONSTRAINT TB_FLOOR_PK PRIMARY KEY (FLOOR_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_FLOOR ADD CONSTRAINT TB_FLOOR_FK FOREIGN KEY (BUILD_SEQ) REFERENCES HoneyArcade.dbo.TB_BUILD(BUILD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SIDO ADD CONSTRAINT TB_SIDO_PK PRIMARY KEY (SIDO_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SIGUNGU ADD CONSTRAINT TB_SIGUNGU_PK PRIMARY KEY (SIGUNGU_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_SIGUNGU ADD CONSTRAINT TB_SIGUNGU_FK FOREIGN KEY (SIDO_SEQ) REFERENCES HoneyArcade.dbo.TB_SIDO(SIDO_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_DONG ADD CONSTRAINT TB_DONG_PK PRIMARY KEY (DONG_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_DONG ADD CONSTRAINT TB_DONG_FK FOREIGN KEY (SIGUNGU_SEQ) REFERENCES HoneyArcade.dbo.TB_SIGUNGU(SIGUNGU_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_COUPON ADD CONSTRAINT TB_COUPON_PK PRIMARY KEY (COUPON_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_COUPON_BUILD ADD CONSTRAINT TB_COUPON_BUILD_PK PRIMARY KEY (COUPON_SEQ,BUILD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_COUPON_DTL ADD CONSTRAINT TB_COUPON_DTL_PK PRIMARY KEY (COUPON_DTL_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_COUPON_DTL ADD CONSTRAINT TB_COUPON_DTL_FK FOREIGN KEY (COUPON_SEQ) REFERENCES HoneyArcade.dbo.TB_COUPON(COUPON_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_BANNER ADD CONSTRAINT TB_BANNER_PK PRIMARY KEY (BANNER_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_BANNER_AREA ADD CONSTRAINT TB_BANNER_AREA_PK PRIMARY KEY (BANNER_SEQ,DONG_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_AD_REQ ADD CONSTRAINT TB_AD_REQ_PK PRIMARY KEY (STORE_AD_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_EVENT ADD CONSTRAINT TB_EVENT_PK PRIMARY KEY (EVENT_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_EVENT_DTL ADD CONSTRAINT TB_EVENT_DTL_PK PRIMARY KEY (EVENT_DTL_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_EVENT_DTL ADD CONSTRAINT TB_EVENT_DTL_FK FOREIGN KEY (EVENT_SEQ) REFERENCES HoneyArcade.dbo.TB_EVENT(EVENT_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_PAY ADD CONSTRAINT TB_PAY_PK PRIMARY KEY (PAY_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_PAY_DTL ADD CONSTRAINT TB_PAY_DTL_PK PRIMARY KEY (PAY_DTL_SEQ);

ALTER TABLE HoneyArcade.dbo.TB_PAY_DTL ADD CONSTRAINT TB_PAY_DTL_FK FOREIGN KEY (PAY_SEQ) REFERENCES HoneyArcade.dbo.TB_PAY(PAY_SEQ);
