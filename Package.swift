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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.10/GrowthBook.zip",
            checksum: "eeedb7cbdcdd0f720db93220d65a71cc0d1b25d08bbf9eaa3b578f88163a58bb"
        ),
    ]
)
