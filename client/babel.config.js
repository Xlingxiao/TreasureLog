module.exports = {
  presets: [
    '@vue/app'
  ],
  "plugins": [
    // 不打印日志
    // "transform-remove-console",
    [
      "component",
      {
        "libraryName": "element-ui",
        "styleLibraryName": "theme-chalk"
      }
    ]
  ]
}
