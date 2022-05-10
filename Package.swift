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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.11/GrowthBook.zip",
            checksum: "eb0f0f4e7895d046532073b68e549d82d0f14af9d3f02a8ae5a0c302dcd89bf2"
        ),
    ]
)
