import { NavLink, useHistory} from "react-router-dom";

  export default function Header({
    loggedIn,
    admin,
    logout,
    fquantity,
    rquantity,
    activUser,
  }) {
    const history = useHistory();
  
    function onClick(e) {
      e.preventDefault();
      logout();
      history.push("/");
    }
  return (
  
    <ul className="header">
      {!admin ? (
        <li>
          <NavLink activeClassName="active" to="/home">
            Home
          </NavLink>
        </li>
      ) : (
        ""
      )}
      {!admin && !loggedIn ? (
        <li>
          <NavLink activeClassName="active" to="/register">
            Register
          </NavLink>
        </li>
      ) : (
        ""
      )}
      {!admin && !loggedIn ? (
        <li>
          <NavLink activeClassName="active" to="/login">
            Log in
          </NavLink>
        </li>
      ) : (
        ""
      )}

      {loggedIn && !admin ? (
        <li>
          <NavLink activeClassName="active" to="/account">
            Profil
          </NavLink>
        </li>
      ) : (
        ""
      )}

      {admin ? (
        <li>
          <NavLink activeClassName="active" to="/users">
            Students
          </NavLink>
        </li>
      ) : (
        ""
      )}

      {!admin ? (
        <li>
          <NavLink activeClassName="active" to="/flow">
            Flows
          </NavLink>
        </li>
      ) : (
        ""
      )}
     
      {admin || loggedIn ? (
        <li>
          <button onClick={onClick}>
            Log out
          </button>
        </li>
      ) : (
        ""
      )}
      <div style={{ color: "white" }}> {activUser}</div>
    </ul>
  );
}