import { Redirect } from "react-router";
import TokenService from "../services/TokenService";

const tokenService = new TokenService()

export default function Logout(props) {
    tokenService.deleteToken()
    props.removeUser()

    return <Redirect to="/"/>
}