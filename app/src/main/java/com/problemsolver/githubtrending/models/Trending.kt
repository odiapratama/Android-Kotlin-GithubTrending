package com.problemsolver.githubtrending.models

data class Trending (
    var author: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var url: String? = null,
    var description: String? = null,
    var language: String? = null,
    var languageColor: String? = null,
    var stars: Long? = null,
    var forks: Long? = null,
    var currentPeriodStars: Long? = null,
    var builtBy: List<BuiltBy>? = null
)

data class BuiltBy (
    var href: String? = null,
    var avatar: String? = null,
    var username: String? = null
)