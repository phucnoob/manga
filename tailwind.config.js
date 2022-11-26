/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        // "src/main/resources/templates/*.html",
        "src/main/resources/templates/**/*.html"
    ],
    theme: {
        extend: {
            fontFamily: {
                'code': ['Source Code Pro', 'sans-serif']
            },
        },

        container: {
            center: true,
            padding: {
                DEFAULT: '4rem',
                sm: '2rem',
                lg: '4rem',
                xl: '5rem',
                '2xl': '6rem',
            }
        }
    },
    plugins: [
        require('tailwind-scrollbar'),
        require('@tailwindcss/line-clamp'),
    ],
}
