// src/main/frontend/src/App.js

import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
  const [hello, setHello] = useState('')

  useEffect(() => {
    axios.get('/greeting?name=React')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
  }, []);

  return (

      <div>
        백엔드에서 가져온 데이터입니다: {JSON.stringify(hello,null,2)}
          <br></br>
        <button>
            <a href="/login">로그인</a>
        </button>
      </div>
  );
}

export default App;

