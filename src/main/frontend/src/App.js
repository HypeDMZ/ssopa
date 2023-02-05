import React, { useState, useEffect } from 'react';
import { Link, Route, Switch, BrowserRouter as Router } from 'react-router-dom';





import Profile from './Profile';



function App() {
    const [user, setUser] = useState(null);
    const authenticated = user != null;


    const logout = () => setUser(null);

    return (
        <Router>
            <header>
                <Link to="/">
                    <button>Home</button>
                </Link>
                <Link to="/profile">
                    <button>Profile</button>
                </Link>
                {authenticated ? (
                    <button onClick={logout}>Logout</button>
                ) : (
                    <Link to="/login">
                        <button>Login</button>
                        <button>hio</button>
                    </Link>
                )}
            </header>
            <hr />
            <main>
                <Switch>




                </Switch>
            </main>
        </Router>
    );
}

export default App;
