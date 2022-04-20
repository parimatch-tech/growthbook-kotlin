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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/archive/refs/tags/GrowthBook-1.2.0.zip",
            checksum: "22cfbbea4c4616132d6ab279181c065d23eae32b4d65efaf33bc2777604d7ce4"
        ),
    ]
)
