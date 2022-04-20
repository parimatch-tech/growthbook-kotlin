// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "GrowthBookLight",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBookLight",
            targets: ["GrowthBookLight"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBookLight",
            path: "./GrowthBookLight.xcframework"
        ),
    ]
)
