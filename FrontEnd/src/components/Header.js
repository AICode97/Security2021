import { useEffect, useState } from "react";
import { NavLink} from "react-router-dom";
import UserService from "../services/UserService";

const userService = new UserService()

export default function Header(props) {
  const isAdmin = props.user && props.user.roles.includes('admin')
  const isUser = props.user && props.user.roles.includes('user')

  return (
    <ul className="header">
      { props.user == null && <li>
        <NavLink exact activeClassName="active" to="/login">
          Log in
        </NavLink>
      </li> }
      { props.user && <li>
        <NavLink exact activeClassName="active" to="/logout">
          Logout
        </NavLink>
      </li> }
      { props.user == null && <li>
        <NavLink activeClassName="active" to="/register">
          Register
        </NavLink>
      </li> }
      {
        isUser && <li>
          <NavLink activeClassName="active" to="/user">
            User
          </NavLink>
        </li>
      }
      { isAdmin && <li>
        <NavLink activeClassName="active" to="/admin">
          Admin
        </NavLink>
      </li>}
    </ul>
  );
}