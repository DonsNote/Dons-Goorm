import Foundation
import Alamofire

final class APIService {
    static let shared = APIService()

    private init() {}

    func request<T: Decodable>(
        _ endpoint: String,
        method: HTTPMethod = .get,
        parameters: Parameters? = nil
    ) async throws -> T {
        let url = "\(Config.apiBaseURL)/api/\(endpoint)"

        return try await withCheckedThrowingContinuation { continuation in
            AF.request(url, method: method, parameters: parameters)
                .validate()
                .responseDecodable(of: T.self) { response in
                    switch response.result {
                    case .success(let value):
                        continuation.resume(returning: value)
                    case .failure(let error):
                        continuation.resume(throwing: error)
                    }
                }
        }
    }
}
