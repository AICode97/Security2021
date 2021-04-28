import React, { useState, useEffect } from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  useLocation,
} from "react-router-dom";


import facade from "./apiFacade";
import LogIn from "./components/LogIn";
import LoggedIn from "./components/LoggedIn";
import Home from "./components/Home";
import Header from "./components/Header";
import Register from "./components/Register";
import Account from "./components/Account";
import Orders from "./components/Orders";
import Users from "./components/Users";
import DogInfo from "./components/DogInfo";
import Statistics from "./components/Statistics";
import MyDogs from "./components/MyDogs";


function App() {
  const init = { username: "", password: "" };
  const [loggedIn, setLoggedIn] = useState(facade.loggedIn);
  const [errorMsg, setErrMsg] = useState("");
  const [activeUser, setActiveUser] = useState(facade.getActivUser);
  const [admin, setAdmin] = useState(false);

  const logout = () => {
    facade.logout();
    setLoggedIn(false);
    setAdmin(false);
    setActiveUser(false);
  };


  const login = (user, pass, callback) => {
    facade
      .login(user, pass)
      .then(() => {
        user !== "admin" ? setLoggedIn(true) : setAdmin(true);
        setActiveUser(user);
      })
      .then(callback)
      .catch((err) => {
        if (err.status) {
          err.fullError.then((e) => setErrMsg(e.message));
        } else {
          setErrMsg("Network error has occurred: could not log in");
          console.log("Network error! Could not log in");
        }
      });
    setErrMsg("");
  };
  console.log("Admin status: " + admin + "  loggedIn status: " + loggedIn);
  return (
    <Router>
      <div className="App">
        <Header
          loggedIn={loggedIn}
          admin={admin}
          logout={logout}
          activeUser={activeUser}
          Search={DogInfo}
          MyDogs={MyDogs}
        />
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          <Route exact path="/search">
              <DogInfo />
          </Route>
          
          
          {!loggedIn ? (
            <Route exact path="/register">
              <Register facade={facade} init={init} />
            </Route>
          ) : (
            ""
          )}
          {!loggedIn ? (
            <Route exact path="/login">
              <LogIn login={login} init={init} errorMsg={errorMsg} />
            </Route>
          ) : (
            ""
          )}
          {loggedIn ? (
            <Route exact path="/account">
              <Account facade={facade} activeUser={activeUser} />
            </Route>
          ) : (
            ""
          )}
          {loggedIn ? (
            <Route exact path="/MyDogs">
              <MyDogs activeUser={activeUser} />
            </Route>
          ) : (
            ""
          )}
          {admin ? (
            <Route exact path="/orders">
              <Orders />
            </Route>
          ) : (
            ""
          )}
          {admin ? (
            <Route exact path="/users">
              <Users />
            </Route>
          ) : (
            ""
          )}
         
          {admin ? (
            <Route exact path="/statistics">
              <Statistics />
            </Route>
          ) : (
            ""
          )}
          <Route>
            <NoMatch />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}
function NoMatch() {
  let location = useLocation();

  return (
    <div>
      <h3>
        No match for <code>{location.pathname}</code>
      </h3>
    </div>
  );
}
export default App;
