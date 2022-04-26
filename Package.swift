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
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.1/GrowthBook.zip",
            checksum: "cf1a9e162cb78aaf380a3dc951f36d360ee22fbeb64e73ff52971da636bfe4ad"
        ),
    ]
)
