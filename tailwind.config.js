/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["src/main/resources/templates/*.html"],
    theme: {
        extend: {
            fontFamily: {
                'code': ['Source Code Pro', 'sans-serif']
            },
        },
    },
    plugins: [
        require('tailwind-scrollbar'),
        require('@tailwindcss/line-clamp'),
    ],
}
