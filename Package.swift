// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "GrowthBook",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBook",
            targets: ["GrowthBook"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBook",
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.8/GrowthBook.zip",
            checksum: "caf1ffbba7bcc95e9eed02ad3fccb19c4d9d8b03e00e9d4519d617f1197a34c1"
        ),
    ]
)
