import { useEffect, useState, Button, Form } from "react";
import { useHistory } from "react-router-dom";
import { Row, Container, Col } from "react-bootstrap";

export default function Register({ facade, init }) {
  const [newUser, setNewUser] = useState(init);
  const [status, setStatus] = useState("");
  const [nickName, setNickName] = useState("Stranger");
  const [pass2, setPass2] = useState("");
  const history = useHistory();

  function onChange(e) {
    const target = e.target;
    const value = target.value;
    const name = target.id;

    setStatus("");

    setNewUser({
      ...newUser,
      [name]: value,
    });
  }
  function onChange1(e) {
    setPass2(e.target.value);
    setStatus("");
  }

  function onSubmit(e) {
    e.preventDefault();
    if (
      pass2.length <= 3 ||
      newUser.email.length <= 3 ||
      newUser.password.length <= 3 ||
      newUser.username.length <= 3
    ) {
      setStatus("Username or password must be longer than 3 characters");
    } else {
      pass2 === newUser.password
        ? facade
            .registerUser(newUser)
            .then((data) => {
              setStatus(data.msg);
              setNickName(data.username);
            })
            .catch((err) => {
              if (err.status) {
                err.fullError.then((e) => setStatus(e.message));
              } else {
                setStatus("Network error has occurred: could not log in");
                console.log("Network error! Could not log in");
              }
            })
        : setStatus("passwords did not match");
      history.push("/login");
    }
  }
  return (
      <div>
          <h2>Create A New Profile</h2>
     
            <h5>{status}</h5>

          <form onSubmit={onSubmit}>
                Email:
                <input type="text" id="email" onChange={onChange} />
                Username:
                <input type="text" id="username" onChange={onChange} />
                Password:
                <input type="password" id="password" onChange={onChange} />
                Repeat password:
                <input type="password" id="password1" onChange={onChange1} />
                <input type="submit" value="Submit" />
            </form>
        </div>
    
  );
}
