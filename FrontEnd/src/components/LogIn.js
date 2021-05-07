import "../RegisterAndLogin.css";
import { useHistory } from "react-router-dom";
import facade from "../apiFacade";
import React, { useState, useEffect } from "react";

export default function LogIn({
  init,
  setActivUser,
  setAdmin,
  setLoggedIn,
}) {
  const [loginCredentials, setLoginCredentials] = useState(init);

  const history = useHistory();
  const [msg, setMsg] = useState("");
  const [start, setStart] = useState(false);
  const [loading, setLoading] = useState(false);
  const loader = (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div className="loader"></div>
    </div>
  );
  const successfulLogin = "Login succeeded";

  const onChange = (evt) => {
    setLoginCredentials({
      ...loginCredentials,
      [evt.target.id]: evt.target.value,
    });
  };

  const startLogin = (evt) => {
    evt.preventDefault();

    if (loginCredentials.username.length < 1) {
      setMsg("username is missng");
    } else if (loginCredentials.password.length < 1) {
      setMsg("password is missing");
    } else {
      setLoading(true);
      setStart(true);
    }
  };

  /*
  p(loginCredentials.username, loginCredentials.password);
    loginCredentials.username !== "admin"
      ? history.push("/account")
      : history.push("/users");
  */

  useEffect(() => {
    let mounted = true;
    if (start) {
      facade
        .login(loginCredentials.username, loginCredentials.password)

        .then(() => {
          if (mounted) {
            setLoading(false);
            setMsg(successfulLogin);
          }
        })
        .catch((err) => {
          if (err.status) {
            err.fullError.then((e) => setMsg(e.message));
          } else {
            setMsg("Network error has occurred: could not log in");
            console.log("Network error! Could not log in");
          }
        });
      return function cleanup() {
        mounted = false;
        setStart(false);
      };
    }
  }, [start]);
  useEffect(() => {
    if (msg === successfulLogin) {
      setTimeout(() => {
        let u = facade.getActivUser;
        u !== "admin" ? setLoggedIn(true) : setAdmin(true);
        setActivUser(u);
        loginCredentials.username !== "admin"
          ? history.push("/account")
          : history.push("/users");
      }, 1500);
    }
  }, [msg]);
  return (
    <div>
      <h2 className="registerHeader">
        {" "}
        <span className="registerLogo"> Woodle </span>{" "}
      </h2>
      <form className="registerContainer" onChange={onChange}>
        <div className="registerHeader">Log in her</div>
        <div>
          <label className="nameField">
            <input
              type="text"
              id="username"
              className="inputField"
              placeholder="Username"
              disabled={loading ? true : false}
            />
          </label>
        </div>
        <div>
          <label className="nameField">
            <input
              type="password"
              id="password"
              className="inputField"
              placeholder="Password"
              disabled={loading ? true : false}
            />
          </label>
        </div>
        <div style={{ textAlign: "center" }}>
          <label className="nameField">{msg}</label>
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        ></div>{" "}
        {loading ? (
          loader
        ) : msg === successfulLogin ? (
          ""
        ) : (
          <button className="makeButton" onClick={startLogin}>
            Login
          </button>
        )}
      </form>
    </div>
  );
}