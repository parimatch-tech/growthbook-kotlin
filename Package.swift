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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/1.2.0/GrowthBook-1.2.0.zip",
            checksum: "6891c772c437e7968f8a217d6d00ee1f7cb2b268e771cdddca4dce579d4c9d4e"
        ),
    ]
)
