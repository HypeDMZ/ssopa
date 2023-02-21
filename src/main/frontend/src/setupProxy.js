const { createProxyMiddleware } = require("http-proxy-middleware")

module.exports = function (app) {
    app.use(
        "/api",
        createProxyMiddleware({
            target: "https://ssopa02.com/api",
            changeOrigin: true,
        })
    )
}