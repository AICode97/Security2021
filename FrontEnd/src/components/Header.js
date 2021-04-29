import { NavLink} from "react-router-dom";
export default function Header() {
  return (
    <ul className="header">
      
      <li>
        <NavLink exact activeClassName="active" to="/">
          Home
        </NavLink>
      </li>
      <li>
        <NavLink activeClassName="active" to="/search">
           Mine Flows
        </NavLink>
      </li>
      <li>
        <NavLink activeClassName="active" to="/Register">
          Register
        </NavLink>
      </li>
      <li>
        <NavLink activeClassName="active" to="/login">
          Log in
        </NavLink>
      </li>
    </ul>
  );
}