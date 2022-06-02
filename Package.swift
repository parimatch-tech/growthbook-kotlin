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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.20/GrowthBook.zip",
            checksum: "cb5694d00a28c4a66ec593f0550898327174013be4aabdfc2949277e459ad6aa"
        ),
    ]
)
