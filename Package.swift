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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/1.2.0/GrowthBook.zip",
            checksum: "c9fc2961b205fed2c73e55421a60f29609bc9279c254eb8e97df6cd93d0ee29f"
        ),
    ]
)
