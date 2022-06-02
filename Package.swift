// swift-tools-version:5.5
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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.21/GrowthBook.zip",
            checksum: "8f829994196503e4fd3247eabe38c1a3ca4b0839ec011dc30bb818c6f108d5f5"
        ),
    ]
)
