import { useState } from "react"
import { Redirect } from "react-router"
import { Link } from "react-router-dom"
import Wrap from "../components/Wrap"
import AuthService from "../services/AuthService"
import BruteForceService from "../services/BruteForceService"
import TokenService from "../services/TokenService"
import UserService from "../services/UserService"

const authService = new AuthService()
const tokenService = new TokenService()
const userService = new UserService()
const bruteForceService = new BruteForceService()

export default function Login(props) {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("")
    const [isLoggedIn, setIsLoggedIn] = useState(tokenService.hasToken())
    const [isAdmin, setIsAdmin] = useState(false)
    const [isAllowed, setIsAllowed] = useState(bruteForceService.isAllowed())

    // Den gør så du kun kan bruge .!@#$%^&*()_+-= og A-Z, og gør at dit username skal være mindst 6 tegn, also no spaces
function validateUsername(username) {
    const regex = /^[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{6,}$/;
    return regex.test(String(username).toLowerCase());
}
    //bruteForceService.purge()

    const isDisabled = username.length === 0 || password.length === 0 || !validateUsername(username)

    const onButtonPress = async () => {
        setError("")
        try {
            await authService.login(username, password)

            const admin = await userService.isAdmin()
            setIsAdmin(admin)

            props.updateUser()

            setIsLoggedIn(true)
        } catch (_) {
            bruteForceService.registerAttempt()

            setIsAllowed(bruteForceService.isAllowed())
        }
    }

    if (!isAllowed) {
        return <p>Du har skrevet forkert kode for mange gange, prøv igen om 15 minutter</p>
    }

    if (isLoggedIn) {
        const path = isAdmin ? '/admin' : '/user'
        return <Redirect to={ path }/>
    }

    return (
        Wrap(<div>
            <label>
                <p>Username:</p>
                <input type='text' name='username' value={ username } onChange={ (event) => setUsername(event.currentTarget.value) } />
            </label>
            <label>
                <p>Password:</p>
                <input type='password' name='password' value={ password } onChange={ (event) => setPassword(event.currentTarget.value) } />
            </label>
            <button onClick={ onButtonPress } disabled={ isDisabled }>
                Login
            </button>
            <Link className="orLink" to="/register">Or register a user</Link>
            { error.length > 0 && <p className="error">{ error }</p> }
        </div>)
    )
}