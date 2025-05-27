// 서버 실행, 라우팅
// 백엔드 서버 메인 진입점

const express = require('express'); // 백엔드 서버를 웹 프레임 워크 서버
const cors = require('cors'); // 모듈 호출 React(3000) <-> Node.js (3001) 통신 허용const app = express();

// 앱 객체, 미들웨어에 등록 하겠다
const app = express();
app.use(cors()); // 포트 충돌 금지
app.use(express.json()); // json 형식으로 request.body를 파싱

// 테스트용 기본 라우트 http://localhost:3001/
app.get('/', (req, res) => {
  res.send('Backend server is running');
});

// 여행지 관련 API 엔드포인트는 여기에 추가 예정
app.use('/api/travels', require('./routes/travelRoutes'));

// 서버 실행
const PORT = 3001;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

/*
  요청이 막힐 경우
  CORS -> PORT -> JSON -> 라우팅 연결 순으로 재 탐색
*/
