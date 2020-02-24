module.exports = {
  presets: [
    '@vue/app'
  ],
  "plugins": [
    "transform-remove-console",
    [
      "component",
      {
        "libraryName": "element-ui",
        "styleLibraryName": "theme-chalk"
      }
    ]
  ]
}
