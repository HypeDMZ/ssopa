import { Cookies } from "react-cookie"

const [cookies,setCookie,removeCookie] = new Cookies()

export const setCookies = (name, value, option) => {
    return cookies.set(name, value, {...option});
}

export const getCookies = (name) => {
    return cookies.get(name)
}

export const removeCookies = (name) => {
    return removeCookie(name)
}